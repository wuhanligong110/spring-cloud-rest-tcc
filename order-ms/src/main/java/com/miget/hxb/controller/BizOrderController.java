package com.miget.hxb.controller;

import com.miget.hxb.domain.BizOrder;
import com.miget.hxb.model.request.OrderPageRequest;
import com.miget.hxb.model.request.OrderStatusPageRequest;
import com.miget.hxb.model.request.PaymentRequest;
import com.miget.hxb.model.request.PlaceOrderRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.model.response.OrderDetailResponse;
import com.miget.hxb.model.response.OrderListResponse;
import com.miget.hxb.page.PageInfo;
import com.miget.hxb.service.BizOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hxb
 */
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class BizOrderController {

    @Autowired
    private BizOrderService orderService;

    @ApiOperation(value = "下单", notes = "生成预订单")
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public ObjectDataResponse<BizOrder> placeOrder(@Valid @RequestBody PlaceOrderRequest request, BindingResult result) {
        return orderService.placeOrder(request);
    }

    @ApiOperation(value = "确认订单", notes = "支付确认")
    @RequestMapping(value = "/orders/confirmation", method = RequestMethod.POST)
    public ObjectDataResponse payConfirm(@Valid @RequestBody PaymentRequest request, BindingResult result) {
        try {
            orderService.payConfirm(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ObjectDataResponse<>(null);
    }

    @ApiOperation(value = "取消订单", notes = "取消订单增加产品库存")
    @RequestMapping(value = "/orders/cancel", method = RequestMethod.POST)
    public ObjectDataResponse cancel(@Valid @RequestBody PaymentRequest request, BindingResult result) {
        orderService.cancel(request);
        return  new ObjectDataResponse<>(null);
    }

    @ApiOperation(value = "用户订单列表", notes = "用户订单分页")
    @RequestMapping(value = "/orders/order", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<OrderListResponse>> userOrderPageList(Long userId, OrderStatusPageRequest request) {
        return orderService.userOrderPageList(userId,request);
    }

    @ApiOperation(value = "订单列表", notes = "订单分页--管理后台")
    @RequestMapping(value = "/orders/pageList", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<BizOrder>> orderPageList(OrderPageRequest request) {
        return orderService.orderPageList(request);
    }

    @ApiOperation(value = "订单明细", notes = "订单明细")
    @RequestMapping(value = "/orders/order/detail/{orderId}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<OrderDetailResponse> orderDetail(@PathVariable Long orderId) {
        return orderService.orderDetail(orderId);
    }

    @ApiOperation(value = "确认签收", notes = "签收")
    @RequestMapping(value = "/orders/received", method = RequestMethod.POST)
    public ObjectDataResponse hasReceived(@Valid @RequestBody PaymentRequest request, BindingResult result) {
        orderService.hasReceived(request);
        return  new ObjectDataResponse<>(null);
    }

}
