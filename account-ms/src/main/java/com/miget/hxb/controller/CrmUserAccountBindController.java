package com.miget.hxb.controller;

import com.github.pagehelper.Page;
import com.miget.hxb.Shift;
import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.domain.CrmUserAccountBind;
import com.miget.hxb.model.request.AccountAddRequest;
import com.miget.hxb.model.request.AccountUnBindRequest;
import com.miget.hxb.model.request.PageRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.page.PageInfo;
import com.miget.hxb.service.CrmUserAccountBindService;
import com.miget.hxb.service.CrmUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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
public class CrmUserAccountBindController {

    @Autowired
    private CrmUserAccountBindService userAccountBindService;

    @Autowired
    private CrmUserService userService;

    @ApiOperation(value = "根据ID获取用户绑定的卡号", notes = "包括绑定的银行卡、支付宝等")
    @RequestMapping(value = "/users/account", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<CrmUserAccountBind>> accountPageList(Long userId, PageRequest request) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        final Page<CrmUserAccountBind> accountList = userAccountBindService.queryUserAccountPageList(userId,request);
        PageInfo<CrmUserAccountBind> pageInfo = new PageInfo<>(accountList);
        return new ObjectDataResponse<>(pageInfo);
    }

    @ApiOperation(value = "用户新增绑卡", notes = "包括绑定的银行卡、支付宝等")
    @RequestMapping(value = "/users/account", method = RequestMethod.POST)
    public ObjectDataResponse<CrmUserAccountBind> accountAdd(Long userId, @Valid @RequestBody AccountAddRequest request, BindingResult error) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        request.setUserId(Math.toIntExact(userId));
        CrmUserAccountBind userAccount = new CrmUserAccountBind();
        BeanUtils.copyProperties(request,userAccount);
        userAccount.setStatus(1);
        userAccountBindService.persistNonNullProperties(userAccount);
        return new ObjectDataResponse<>(userAccount);
    }

    @ApiOperation(value = "用户解绑账号", notes = "将绑定状态设置为无效")
    @RequestMapping(value = "/users/account", method = RequestMethod.DELETE)
    public ObjectDataResponse accountUnBind(Long userId, @Valid @RequestBody AccountUnBindRequest request, BindingResult error) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        request.setUserId(Math.toIntExact(userId));
        userAccountBindService.accountUnBind(request);
        return new ObjectDataResponse<>(null);
    }

    @ApiOperation(value = "查询用户指定类型的绑卡信息", notes = "包括绑定的银行卡、支付宝等")
    @RequestMapping(value = "/users/accountType/{accountType}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<CrmUserAccountBind> userAccountTypeInfo(Long userId, @PathVariable Integer accountType) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        CrmUserAccountBind userAccountBind = userAccountBindService.queryUserAccountTypeInfo(userId,accountType);
        return new ObjectDataResponse<>(userAccountBind);
    }

    @ApiOperation(value = "查询用户指定账户的信息", notes = "")
    @RequestMapping(value = "/users/account/{userAccount}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<CrmUserAccountBind> userAccountInfo(Long userId, @PathVariable Integer userAccount) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        CrmUserAccountBind userAccountBind = userAccountBindService.queryUserAccountInfo(userId,userAccount);
        return new ObjectDataResponse<>(userAccountBind);
    }

}
