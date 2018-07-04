package com.miget.hxb.controller;

import com.github.pagehelper.Page;
import com.google.common.base.Preconditions;
import com.miget.hxb.Shift;
import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.model.request.CreditAddRequest;
import com.miget.hxb.model.request.CreditStatusRequest;
import com.miget.hxb.model.request.CreditSubRequest;
import com.miget.hxb.model.request.UserCreditDetailRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.model.response.UserCreditDetailResponse;
import com.miget.hxb.page.PageInfo;
import com.miget.hxb.service.BizCreditDetailService;
import com.miget.hxb.service.CrmUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author hxb
 */
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class CrmUserCreditController {

    @Autowired
    private BizCreditDetailService creditDetailService;

    @Autowired
    private CrmUserService userService;

    @ApiOperation(value = "根据ID获取用户积分明细", notes = "包括可提现积分明细、购物积分明细等")
    @RequestMapping(value = "/users/credit", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<UserCreditDetailResponse>> creditPageList(Long userId, @Valid @ModelAttribute UserCreditDetailRequest request) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        Preconditions.checkNotNull(request);
        request.setUserId(Math.toIntExact(userId));
        final Page<UserCreditDetailResponse> creditList = creditDetailService.queryUserCreditPageList(request);
        PageInfo<UserCreditDetailResponse> pageInfo = new PageInfo<>(creditList);
        return new ObjectDataResponse<>(pageInfo);
    }

    @ApiOperation(value = "用户新增积分", notes = "发放积分")
    @RequestMapping(value = "/users/credit/add", method = RequestMethod.POST)
    public ObjectDataResponse creditAdd(Long userId, @Valid @RequestBody CreditAddRequest request, BindingResult error) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        request.setUserId(Math.toIntExact(userId));
        creditDetailService.creditAdd(request);
        return new ObjectDataResponse<>(null);
    }

    @ApiOperation(value = "批量新增积分", notes = "批量发放积分")
    @RequestMapping(value = "/users/credit/batch", method = RequestMethod.POST)
    public ObjectDataResponse creditBatchAdd(@Valid @RequestBody List<CreditAddRequest> requests, BindingResult error) {
        creditDetailService.creditBatchAdd(requests);
        return new ObjectDataResponse<>(null);
    }

    @ApiOperation(value = "用户预消耗积分", notes = "预消耗积分")
    @RequestMapping(value = "/users/credit/sub", method = RequestMethod.POST)
    public ObjectDataResponse preCreditSub(Long userId, @Valid @RequestBody CreditSubRequest request, BindingResult error) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        request.setUserId(Math.toIntExact(userId));
        creditDetailService.creditSub(request);
        return new ObjectDataResponse<>(null);
    }

    @ApiOperation(value = "用户积分状态", notes = "积分状态")
    @RequestMapping(value = "/users/credit/status", method = RequestMethod.POST)
    public ObjectDataResponse creditStatus(Long userId, @Valid @RequestBody CreditStatusRequest request, BindingResult error) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        request.setUserId(Math.toIntExact(userId));
        creditDetailService.updataCreditStatus(request);
        return new ObjectDataResponse<>(null);
    }

}
