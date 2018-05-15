package com.github.prontera.controller;

import com.github.pagehelper.Page;
import com.github.prontera.Delay;
import com.github.prontera.RandomlyThrowsException;
import com.github.prontera.Shift;
import com.github.prontera.domain.CrmUser;
import com.github.prontera.domain.CrmUserAddress;
import com.github.prontera.model.request.AddressAddRequest;
import com.github.prontera.model.request.PageRequest;
import com.github.prontera.model.response.ObjectDataResponse;
import com.github.prontera.page.PageInfo;
import com.github.prontera.service.CrmUserAddressService;
import com.github.prontera.service.CrmUserService;
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
public class CrmUserAddressController {

    @Autowired
    private CrmUserAddressService userAddressService;

    @Autowired
    private CrmUserService userService;

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "根据ID获取用户收件地址", notes = "")
    @RequestMapping(value = "/users/{userId}/address", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<CrmUserAddress>> addressPageList(@PathVariable Long userId,PageRequest request) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        final Page<CrmUserAddress> productList = userAddressService.queryUserAddressPageList(userId,request);
        PageInfo<CrmUserAddress> pageInfo = new PageInfo<>(productList);
        return new ObjectDataResponse<>(pageInfo);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "用户新增收件地址", notes = "添加新的收件地址")
    @RequestMapping(value = "/users/{userId}/address", method = RequestMethod.POST)
    public ObjectDataResponse<CrmUserAddress> addressAdd(@PathVariable Long userId, @Valid @RequestBody AddressAddRequest request, BindingResult error) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        request.setUserId(Math.toIntExact(userId));
        CrmUserAddress userAddress = new CrmUserAddress();
        BeanUtils.copyProperties(request,userAddress);
        userAddressService.persistNonNullProperties(userAddress);
        return new ObjectDataResponse<>(userAddress);
    }

}
