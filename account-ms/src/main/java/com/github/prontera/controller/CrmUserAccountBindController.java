package com.github.prontera.controller;

import com.github.pagehelper.Page;
import com.github.prontera.Delay;
import com.github.prontera.RandomlyThrowsException;
import com.github.prontera.Shift;
import com.github.prontera.domain.CrmUser;
import com.github.prontera.domain.CrmUserAccountBind;
import com.github.prontera.domain.CrmUserAddress;
import com.github.prontera.model.request.*;
import com.github.prontera.model.response.ObjectDataResponse;
import com.github.prontera.page.PageInfo;
import com.github.prontera.service.CrmUserAccountBindService;
import com.github.prontera.service.CrmUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;

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

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "根据ID获取用户绑定的卡号", notes = "包括绑定的银行卡、支付宝等")
    @RequestMapping(value = "/users/{userId}/account", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<CrmUserAccountBind>> accountPageList(@PathVariable Long userId, PageRequest request) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        final Page<CrmUserAccountBind> accountList = userAccountBindService.queryUserAccountPageList(userId,request);
        PageInfo<CrmUserAccountBind> pageInfo = new PageInfo<>(accountList);
        return new ObjectDataResponse<>(pageInfo);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "用户新增绑卡", notes = "包括绑定的银行卡、支付宝等")
    @RequestMapping(value = "/users/{userId}/account", method = RequestMethod.POST)
    public ObjectDataResponse<CrmUserAccountBind> accountAdd(@PathVariable Long userId, @Valid @RequestBody AccountAddRequest request, BindingResult error) {
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

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "用户解绑账号", notes = "将绑定状态设置为无效")
    @RequestMapping(value = "/users/{userId}/account", method = RequestMethod.DELETE)
    public ObjectDataResponse accountUnBind(@PathVariable Long userId, @Valid @RequestBody AccountUnBindRequest request, BindingResult error) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        request.setUserId(Math.toIntExact(userId));
        userAccountBindService.accountUnBind(request);
        return new ObjectDataResponse<>(null);
    }

}
