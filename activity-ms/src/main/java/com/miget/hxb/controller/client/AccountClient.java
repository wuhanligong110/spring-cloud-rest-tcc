package com.miget.hxb.controller.client;

import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.model.response.ObjectDataResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = API_PATH + "/users/{openid}/weixin", method = RequestMethod.GET)
    ObjectDataResponse<CrmUser> queryRemoteUserByOpenId(@PathVariable("openid")String openid);

    @RequestMapping(value = API_PATH + "/users/weixin", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    ObjectDataResponse<Integer> weixinRemoteRegister(@RequestBody CrmUser user);
}
