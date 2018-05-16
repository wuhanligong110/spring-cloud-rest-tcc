package com.miget.hxb.controller;

import com.miget.hxb.Delay;
import com.miget.hxb.RandomlyThrowsException;
import com.miget.hxb.domain.Order;
import com.miget.hxb.model.request.PaymentRequest;
import com.miget.hxb.model.request.PlaceOrderRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Zhao Junjian
 */
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "下单", notes = "生成预订单")
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public ObjectDataResponse<Order> placeOrder(@Valid @RequestBody PlaceOrderRequest request, BindingResult result) {
        return orderService.placeOrder(request);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "确认订单", notes = "支付及确认")
    @RequestMapping(value = "/orders/confirmation", method = RequestMethod.POST)
    public ObjectDataResponse<Order> payOff(@Valid @RequestBody PaymentRequest request, BindingResult result) {
        return orderService.confirm(request);
    }
}
