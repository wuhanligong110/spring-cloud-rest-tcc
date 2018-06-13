package com.miget.hxb.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.controller.client.AccountClient;
import com.miget.hxb.controller.client.ProductClient;
import com.miget.hxb.domain.BizOrder;
import com.miget.hxb.domain.BizOrderItem;
import com.miget.hxb.model.CimProduct;
import com.miget.hxb.model.CrmUser;
import com.miget.hxb.model.request.*;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.page.PageInfo;
import com.miget.hxb.persistence.BizOrderMapper;
import com.miget.hxb.persistence.CrudMapper;
import com.miget.hxb.sender.RabbitSender;
import com.miget.hxb.util.MQConstants;
import com.miget.hxb.util.RabbitMetaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zhao Junjian
 */
@Service
public class BizOrderService extends CrudServiceImpl<BizOrder> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BizOrderService.class);

    @Autowired
    private AccountClient accountClient;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private BizOrderItemService orderItemService;
    @Autowired
    private RabbitSender rabbitSender;

    @Resource
    private BizOrderMapper mapper;

    @Autowired
    public BizOrderService(CrudMapper<BizOrder> mapper) {
        super(mapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public ObjectDataResponse<BizOrder> placeOrder(PlaceOrderRequest request) {
        Preconditions.checkNotNull(request);
        final Long userId = Preconditions.checkNotNull(request.getUserId());
        final List<PlaceOrderItemRequest> orderItems = Preconditions.checkNotNull(request.getOrderItems());
        // 查询用户
        final CrmUser user = findRemoteUser(userId);
        // 获取产品  预定单减库存
        final Map<Integer,CimProduct> productMap = orderProductInventory(orderItems);

        //用数据库中的数据计算订单以及订单明细
        Long orderAmount = 0l;
        List<BizOrderItem> orderItemList = new ArrayList<>();
        for(PlaceOrderItemRequest orderItem : orderItems){
            BizOrderItem item = new BizOrderItem();
            BeanUtils.copyProperties(orderItem,item);
            CimProduct product = productMap.get(orderItem.getProductId());
            item.setBusinessId(product.getBusinessId());
            item.setAmount(orderItem.getProductCount()*product.getPrice());
            item.setProductPrice(Math.toIntExact(product.getPrice()));
            item.setSendStatus(0);
            item.setPayStatus(0);
            item.setOperator("system");
            item.setCreateTime(OffsetDateTime.now());
            orderAmount += item.getAmount();
            orderItemList.add(item);
        }

        // 构建订单
        final BizOrder order = new BizOrder();
        order.setUserId(user.getUserId());
        order.setAddressId(request.getAddressId());
        order.setAmount(orderAmount);
        order.setIsPayed(0);
        order.setOperator("system");
        order.setOutTradeNo(request.getOutTradeNo());
        persistNonNullProperties(order);

        for(BizOrderItem orderItem : orderItemList){
            orderItem.setOrderId(order.getOrderId());
        }
        orderItemService.batchInsert(orderItemList);
        return new ObjectDataResponse<>(order);
    }

    private CrmUser findRemoteUser(Long userId) {
        Preconditions.checkNotNull(userId);
        final CrmUser user = accountClient.findUser(userId).getData();
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        return user;
    }

    private Map<Integer,CimProduct> findRemoteProducts(List<Integer> productIds) {
        Preconditions.checkNotNull(productIds);
        Map<Integer,CimProduct> resultMap = new HashMap<>();
        if(productIds.size() > 0){
            resultMap = productClient.findProducts(productIds).getData();
            for (CimProduct product : resultMap.values()) {
                if (product == null) {
                    Shift.fatal(StatusCode.PRODUCT_NOT_EXISTS);
                }
                // 检查库存
                if (product.getHavaInventory() >= product.getInventory()) {
                    Shift.fatal(StatusCode.INSUFFICIENT_PRODUCT);
                }
            }
        }else{
            Shift.fatal(StatusCode.NULL_PRODUCT);
        }

        return resultMap;
    }

    private Map<Integer,CimProduct> orderProductInventory(List<PlaceOrderItemRequest> preOrderRequests) {
        Preconditions.checkNotNull(preOrderRequests);
        Map<Integer,CimProduct> resultMap = new HashMap<>();
        if(preOrderRequests.size() > 0){
            resultMap = productClient.orderProductInventory(preOrderRequests).getData();
            for (CimProduct product : resultMap.values()) {
                if (product == null) {
                    Shift.fatal(StatusCode.PRODUCT_NOT_EXISTS);
                }
                // 检查库存
                if (product.getHavaInventory() >= product.getInventory()) {
                    Shift.fatal(StatusCode.INSUFFICIENT_PRODUCT);
                }
            }
        }else{
            Shift.fatal(StatusCode.NULL_PRODUCT);
        }

        return resultMap;
    }

    public int payConfirm(PaymentRequest request) throws Exception {
        Preconditions.checkNotNull(request);
        BizOrder order = mapper.queryOrderByTradeNo(request.getOutTradeNo());
        if(order != null){
            request.setOrderId(order.getOrderId());
            orderItemService.payConfirm(request);
            mapper.payConfirm(request);
            /** 生成一个发送对象 */
            RabbitMetaMessage rabbitMetaMessage = new RabbitMetaMessage();
            /**设置交换机 */
            rabbitMetaMessage.setExchange(MQConstants.BUSINESS_EXCHANGE);
            /**指定routing key */
            rabbitMetaMessage.setRoutingKey(MQConstants.BUSINESS_KEY);
            /** 设置需要传递的消息体,可以是任意对象 */
            rabbitMetaMessage.setPayload("the message you want to send");
            //do some biz
            /** 发送消息 */
            rabbitSender.send(rabbitMetaMessage);
        }
        return 0;
    }

    public int cancel(PaymentRequest request) {
        Preconditions.checkNotNull(request);
        List<BizOrderItem> orderItems = orderItemService.orderDetailList(request.getOrderId());
        Preconditions.checkNotNull(orderItems);
        if(orderItems.size() > 0){
            List<OrderCancelRequest> orderCancelRequests = new ArrayList<>();
            for(BizOrderItem orderItem : orderItems){
                OrderCancelRequest orderCancelRequest = new OrderCancelRequest();
                orderCancelRequest.setProductCount(orderItem.getProductCount());
                orderCancelRequest.setProductId(orderItem.getProductId());
                orderCancelRequests.add(orderCancelRequest);
            }
            Preconditions.checkNotNull(orderCancelRequests);
            int result = orderCancel(orderCancelRequests);
            return  result;
        }
        return 0;
    }

    private int orderCancel(List<OrderCancelRequest> orderCancelRequests) {
        Preconditions.checkNotNull(orderCancelRequests);
        if(orderCancelRequests.size() > 0){
            int result = productClient.orderCancel(orderCancelRequests).getData();
            if(result < 1){
                Shift.fatal(StatusCode.FAIL_CANCEL_ORDER);
            }
            return result;
        }else{
            Shift.fatal(StatusCode.WITHOUT_CANCEL_ORDER);
        }
        return 0;
    }

    public ObjectDataResponse<PageInfo<BizOrder>> userOrderPageList(Long userId, PageRequest request) {
        Preconditions.checkNotNull(userId);
        Preconditions.checkNotNull(request);
        final CrmUser user = findRemoteUser(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        final Page<BizOrder> orderList = mapper.userOrderPageList(userId);
        PageInfo<BizOrder> pageInfo = new PageInfo<>(orderList);
        return new ObjectDataResponse<>(pageInfo);
    }

    public ObjectDataResponse<PageInfo<BizOrder>> orderPageList(OrderPageRequest request) {
        Preconditions.checkNotNull(request);
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        final Page<BizOrder> orderList = mapper.orderPageList(request.getBusinessId());
        PageInfo<BizOrder> pageInfo = new PageInfo<>(orderList);
        return new ObjectDataResponse<>(pageInfo);
    }

    public ObjectDataResponse<List<BizOrderItem>> orderDetailList(Long orderId) {
        Preconditions.checkNotNull(orderId);
        List<BizOrderItem> orderItems = orderItemService.orderDetailList(orderId);
        return new ObjectDataResponse<>(orderItems);
    }
}
