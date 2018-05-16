package com.miget.hxb.controller.client;

import com.miget.hxb.model.Product;
import com.miget.hxb.model.request.StockReservationRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.model.response.ReservationResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = API_PATH + "/products/{id}", method = RequestMethod.GET)
    ObjectDataResponse<Product> findProduct(@PathVariable("id") Long productId);

    @RequestMapping(value = API_PATH + "/stocks/reservation", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    ReservationResponse reserve(@RequestBody StockReservationRequest request);

}
