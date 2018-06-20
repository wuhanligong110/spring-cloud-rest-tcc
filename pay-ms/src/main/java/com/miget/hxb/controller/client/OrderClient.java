package com.miget.hxb.controller.client;

import com.miget.hxb.domain.BizOrder;
import com.miget.hxb.model.request.PaymentRequest;
import com.miget.hxb.model.request.PlaceOrderRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.model.response.OrderDetailResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Zhao Junjian
 */
@FeignClient(name = OrderClient.SERVICE_ID, fallback = OrderClientFallback.class)
public interface OrderClient {
    /**
     * eureka service name
     */
    String SERVICE_ID = "order";
    /**
     * common api prefix
     */
    String API_PATH = "/api/v1";

    @RequestMapping(value = API_PATH + "/orders", method = RequestMethod.POST)
    ObjectDataResponse<BizOrder> placeOrder(@RequestBody PlaceOrderRequest request);

    @RequestMapping(value = API_PATH + "/orders/confirmation", method = RequestMethod.POST)
    ObjectDataResponse payConfirm(@RequestBody PaymentRequest request);

    @RequestMapping(value = API_PATH + "/orders/order/detail/{orderId}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    ObjectDataResponse<OrderDetailResponse> orderDetail(@PathVariable("orderId") Long orderId);

}
