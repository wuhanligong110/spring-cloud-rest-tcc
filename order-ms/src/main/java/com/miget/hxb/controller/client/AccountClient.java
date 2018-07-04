package com.miget.hxb.controller.client;

import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.domain.CrmUserAddress;
import com.miget.hxb.model.response.ObjectDataResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hxb
 */
@FeignClient(name = AccountClient.SERVICE_ID, fallback = AccountClientFallback.class)
public interface AccountClient {
    /**
     * eureka service name
     */
    String SERVICE_ID = "account";
    /**
     * common api prefix
     */
    String API_PATH = "/api/v1";

    @RequestMapping(value = API_PATH + "/users", method = RequestMethod.GET)
    ObjectDataResponse<CrmUser> findUser(@RequestParam("userId") Long userId);

    @RequestMapping(value = API_PATH +  "/users/address/detail/{addressId}", method = RequestMethod.GET)
    ObjectDataResponse<CrmUserAddress> userAddressDetail(@PathVariable("addressId") Long addressId);

}
