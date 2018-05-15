package com.github.prontera.controller;

import com.github.prontera.Delay;
import com.github.prontera.RandomlyThrowsException;
import com.github.prontera.Shift;
import com.github.prontera.domain.CrmUser;
import com.github.prontera.model.request.LoginRequest;
import com.github.prontera.model.request.RechargeRequest;
import com.github.prontera.model.request.RegisterRequest;
import com.github.prontera.model.response.LoginResponse;
import com.github.prontera.model.response.ObjectDataResponse;
import com.github.prontera.model.response.RegisterResponse;
import com.github.prontera.service.CrmUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hxb
 */
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class CrmUserController {
    @Autowired
    private CrmUserService crmUserService;

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "根据ID获取用户", notes = "")
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<CrmUser> findUser(@PathVariable Long userId) {
        final CrmUser user = crmUserService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }else{
            user.setPassword(null);
            user.setPwdSalt(null);
        }
        return new ObjectDataResponse<>(user);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "用户注册", notes = "注册新用户, 余额自定义, 用于下单等一系列操作, 并可获取JWT")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request, BindingResult error) {
        return crmUserService.register(request);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "用户登录", notes = "用于用户登录, 可获取JWT")
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public LoginResponse login(@Valid @RequestBody LoginRequest request, BindingResult error) {
        return crmUserService.login(request);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "用户余额变更", notes = "直接变更指定用户的余额")
    @RequestMapping(value = "/users/{userId}/balance", method = RequestMethod.PATCH)
    public ObjectDataResponse<CrmUser> recharge(@PathVariable Long userId, @Valid @RequestBody RechargeRequest request, BindingResult error) {
        final CrmUser user = crmUserService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        user.setAccWithdrawalCredit(request.getAmount());
        crmUserService.updateNonNullProperties(user);
        return new ObjectDataResponse<>(user);
    }

}
