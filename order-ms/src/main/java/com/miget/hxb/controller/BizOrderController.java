package com.miget.hxb.controller;

import com.miget.hxb.Delay;
import com.miget.hxb.RandomlyThrowsException;
import com.miget.hxb.domain.BizOrder;
import com.miget.hxb.domain.BizOrderItem;
import com.miget.hxb.model.request.OrderPageRequest;
import com.miget.hxb.model.request.PageRequest;
import com.miget.hxb.model.request.PaymentRequest;
import com.miget.hxb.model.request.PlaceOrderRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.page.PageInfo;
import com.miget.hxb.sender.RabbitSender;
import com.miget.hxb.service.BizOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author hxb
 */
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class BizOrderController {

    @Autowired
    private BizOrderService orderService;

    @Autowired
    private RabbitSender rabbitSender;

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "下单", notes = "生成预订单")
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public ObjectDataResponse<BizOrder> placeOrder(@Valid @RequestBody PlaceOrderRequest request, BindingResult result) {
        return orderService.placeOrder(request);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "确认订单", notes = "支付确认")
    @RequestMapping(value = "/orders/confirmation", method = RequestMethod.POST)
    public ObjectDataResponse payConfirm(@Valid @RequestBody PaymentRequest request, BindingResult result) {
        orderService.payConfirm(request);
        return  new ObjectDataResponse<>(null);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "取消订单", notes = "取消订单增加产品库存")
    @RequestMapping(value = "/orders/cancel", method = RequestMethod.POST)
    public ObjectDataResponse cancel(@Valid @RequestBody PaymentRequest request, BindingResult result) {
        orderService.cancel(request);
        return  new ObjectDataResponse<>(null);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "用户订单列表", notes = "用户订单分页")
    @RequestMapping(value = "/orders/{userId}/order", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<BizOrder>> userOrderPageList(@PathVariable Long userId, PageRequest request) {
        return orderService.userOrderPageList(userId,request);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "订单列表", notes = "订单分页--管理后台")
    @RequestMapping(value = "/orders/order", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<BizOrder>> orderPageList(OrderPageRequest request) {
        return orderService.orderPageList(request);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "订单明细", notes = "订单明细")
    @RequestMapping(value = "/orders/{orderId}/order/detail", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<List<BizOrderItem>> orderDetailList(@PathVariable Long orderId) {
        return orderService.orderDetailList(orderId);
    }

}
