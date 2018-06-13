package com.miget.hxb.controller.client;

import com.miget.hxb.model.CrmUser;
import com.miget.hxb.model.response.ObjectDataResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @RequestMapping(value = API_PATH + "/users/{userId}", method = RequestMethod.GET)
    ObjectDataResponse<CrmUser> findUser(@PathVariable("userId") Long userId);

    @RequestMapping(value = "/users/weixin/userinfo", method = RequestMethod.GET)
    ObjectDataResponse<CrmUser> weixinUserinfo(HttpServletRequest request, HttpServletResponse response);

}
