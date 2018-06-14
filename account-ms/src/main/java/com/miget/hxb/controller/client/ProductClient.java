package com.miget.hxb.controller.client;

import com.miget.hxb.domain.SysBusinessWeixinConfig;
import com.miget.hxb.model.response.ObjectDataResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/weixin/{businessId}/config", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    ObjectDataResponse<SysBusinessWeixinConfig> weixinConfig(@PathVariable("businessId") Long businessId);

}
