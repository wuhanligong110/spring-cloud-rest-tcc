package com.miget.hxb.controller.client;

import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.domain.CrmUserAccountBind;
import com.miget.hxb.model.request.CreditStatusRequest;
import com.miget.hxb.model.request.CreditSubRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

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

    @RequestMapping(value = API_PATH + "/users/{openid}/weixin", method = RequestMethod.GET)
    ObjectDataResponse<CrmUser> queryRemoteUserByOpenId(@PathVariable("openid")String openid);

    @RequestMapping(value = API_PATH + "/users/{userId}/account/{userAccount}", method = RequestMethod.GET)
    ObjectDataResponse<CrmUserAccountBind> userAccountInfo(@PathVariable("userId") Long userId, @PathVariable("userAccount") Integer userAccount);

    @RequestMapping(value = API_PATH + "/users/{userId}/credit/sub", method = RequestMethod.POST)
    ObjectDataResponse creditSub(@PathVariable("userId") Long userId, @Valid @RequestBody CreditSubRequest request);

    @RequestMapping(value = API_PATH + "/users/{userId}/credit/status", method = RequestMethod.POST)
    ObjectDataResponse creditStatus(@PathVariable("userId") Long userId, @Valid @RequestBody CreditStatusRequest request);
}
