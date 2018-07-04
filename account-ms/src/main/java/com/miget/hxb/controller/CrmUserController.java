package com.miget.hxb.controller;

import com.miget.hxb.Shift;
import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.model.request.LoginRequest;
import com.miget.hxb.model.request.RechargeRequest;
import com.miget.hxb.model.request.RegisterRequest;
import com.miget.hxb.model.response.LoginResponse;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.model.response.RegisterResponse;
import com.miget.hxb.service.CrmUserService;
import com.miget.hxb.service.WeixinService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author hxb
 */
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class CrmUserController {
    @Autowired
    private CrmUserService crmUserService;
    @Resource
    private WeixinService weixinService;

    @ApiOperation(value = "根据ID获取用户", notes = "根据userId获取用户")
    @RequestMapping(value = "/users", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<CrmUser> findUser(Long userId) {
        final CrmUser user = crmUserService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }else{
            user.setPassword(null);
            user.setPwdSalt(null);
        }
        return new ObjectDataResponse<>(user);
    }

    @ApiOperation(value = "用户注册", notes = "注册新用户, 余额自定义, 用于下单等一系列操作, 并可获取JWT")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request, BindingResult error) {
        return crmUserService.register(request);
    }

    @ApiOperation(value = "用户登录", notes = "用于用户登录, 可获取JWT")
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ObjectDataResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request, BindingResult error) {
        return crmUserService.login(request);
    }

    @ApiOperation(value = "微信用户登录", notes = "登录")
    @RequestMapping(value = "/users/weixin/login", method = RequestMethod.POST)
    public ObjectDataResponse<LoginResponse> weixinLogin(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据微信获取用户");
        String openId = weixinService.getUserOpenId(request,response);
        if(StringUtils.isBlank(openId)){
            Shift.fatal(StatusCode.WEIXIN_OPENID_ERROR);
        }
        final CrmUser user = crmUserService.queryUserByOpenId(openId);
        if(user == null){
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(Long.valueOf(user.getUserId()));
        loginResponse.setMobile(user.getMobile());
        return new ObjectDataResponse<>(loginResponse);
    }

    @ApiOperation(value = "根据微信openid获取用户", notes = "根据微信openid获取用户")
    @RequestMapping(value = "/users/{openid}/weixin", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    ObjectDataResponse<CrmUser> queryRemoteUserByOpenId(@PathVariable("openid")String openid){
        final CrmUser user = crmUserService.queryUserByOpenId(openid);
        return new ObjectDataResponse<>(user);
    }

    @ApiOperation(value = "微信用户注册", notes = "微信用户注册")
    @RequestMapping(value = "/users/weixin", method = RequestMethod.POST)
    ObjectDataResponse<Integer> weixinRemoteRegister(@RequestBody CrmUser user){
        int result = crmUserService.persist(user);
        return new ObjectDataResponse<>(result);
    }

    @ApiOperation(value = "根据微信获取用户", notes = "获取用户")
    @RequestMapping(value = "/users/weixin/userinfo", method = RequestMethod.GET)
    public ObjectDataResponse<CrmUser> weixinUserinfo(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据微信获取用户");
        String openId = weixinService.getUserOpenId(request,response);
        if(StringUtils.isBlank(openId)){
            Shift.fatal(StatusCode.WEIXIN_OPENID_ERROR);
        }
        final CrmUser user = crmUserService.queryUserByOpenId(openId);
        if(user == null){
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        return new ObjectDataResponse<>(user);
    }

    @ApiOperation(value = "用户余额变更", notes = "直接变更指定用户的余额")
    @RequestMapping(value = "/users/balance", method = RequestMethod.PATCH)
    public ObjectDataResponse<CrmUser> recharge(Long userId,@Valid @RequestBody RechargeRequest request, BindingResult error) {
        final CrmUser user = crmUserService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        user.setAccWithdrawalCredit(request.getAmount());
        crmUserService.updateNonNullProperties(user);
        return new ObjectDataResponse<>(user);
    }

    /*@PostMapping("/detail")
    public void userDetail(HttpServletRequest request,String userId) {
        System.out.println(userId);
        String xxxxToken = request.getHeader("token");
        String xxxxAuthen = request.getHeader("authorization");
        String tokenss = request.getAttribute("token").toString();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();
    }*/

}
