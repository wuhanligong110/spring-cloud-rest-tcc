package com.miget.hxb.controller.client;

import com.miget.hxb.domain.CimProduct;
import com.miget.hxb.model.request.OrderCancelRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @author Zhao Junjian
 */
@FeignClient(name = ProductClient.SERVICE_ID, fallback = ProductClientFallback.class)
public interface ProductClient {
    /**
     * eureka service name
     */
    String SERVICE_ID = "product";
    /**
     * common api prefix
     */
    String API_PATH = "/api/v1";

    @RequestMapping(value = API_PATH + "/products", method = RequestMethod.GET)
    ObjectDataResponse<Map<Integer,CimProduct>> findProducts(List<Integer> productIds);

    @RequestMapping(value = API_PATH + "/products/inventory", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    ObjectDataResponse<Integer> orderCancel(@RequestBody List<OrderCancelRequest> orderCancelRequests);
}
