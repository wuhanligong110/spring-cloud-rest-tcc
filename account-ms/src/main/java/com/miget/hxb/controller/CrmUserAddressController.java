package com.miget.hxb.controller;

import com.github.pagehelper.Page;
import com.miget.hxb.Delay;
import com.miget.hxb.RandomlyThrowsException;
import com.miget.hxb.Shift;
import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.domain.CrmUserAddress;
import com.miget.hxb.model.request.AddressAddRequest;
import com.miget.hxb.model.request.AddressDeleteRequest;
import com.miget.hxb.model.request.PageRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.page.PageInfo;
import com.miget.hxb.service.CrmUserAddressService;
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
public class CrmUserAddressController {

    @Autowired
    private CrmUserAddressService userAddressService;

    @Autowired
    private CrmUserService userService;

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "根据ID获取用户收件地址", notes = "")
    @RequestMapping(value = "/users/{userId}/address", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<CrmUserAddress>> addressPageList(@PathVariable Long userId, PageRequest request) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        final Page<CrmUserAddress> addressList = userAddressService.queryUserAddressPageList(userId,request);
        PageInfo<CrmUserAddress> pageInfo = new PageInfo<>(addressList);
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

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "用户删除收件地址", notes = "删除收件地址")
    @RequestMapping(value = "/users/{userId}/address", method = RequestMethod.DELETE)
    public ObjectDataResponse addressDelete(@PathVariable Long userId, @Valid @RequestBody AddressDeleteRequest request, BindingResult error) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        request.setUserId(Math.toIntExact(userId));
        userAddressService.addressDelete(request);
        return new ObjectDataResponse<>(null);
    }

}
