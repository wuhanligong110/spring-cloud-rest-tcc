package com.miget.hxb.controller;

import com.github.pagehelper.Page;
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

    @ApiOperation(value = "根据ID获取用户收件地址", notes = "")
    @RequestMapping(value = "/users/address", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<CrmUserAddress>> addressPageList(Long userId, PageRequest request) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        final Page<CrmUserAddress> addressList = userAddressService.queryUserAddressPageList(userId,request);
        PageInfo<CrmUserAddress> pageInfo = new PageInfo<>(addressList);
        return new ObjectDataResponse<>(pageInfo);
    }

    @ApiOperation(value = "根据ID获取用户默认收件地址", notes = "")
    @RequestMapping(value = "/users/address/default", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<CrmUserAddress> userDefaultAddress(Long userId) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        final CrmUserAddress defaultAddress = userAddressService.queryUserDefaultAddress(userId);
        return new ObjectDataResponse<>(defaultAddress);
    }

    @ApiOperation(value = "用户编辑收件地址", notes = "编辑收件地址")
    @RequestMapping(value = "/users/address", method = RequestMethod.POST)
    public ObjectDataResponse<CrmUserAddress> addressEdit(Long userId, @Valid @RequestBody AddressAddRequest request, BindingResult error) {
        final CrmUser user = userService.queryByUserId(userId);
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        request.setUserId(Math.toIntExact(userId));
        CrmUserAddress userAddress = new CrmUserAddress();
        BeanUtils.copyProperties(request,userAddress);
        if(userAddress.getId() != null){
            userAddressService.updateNonNullProperties(userAddress);
        }else {
            userAddressService.persistNonNullProperties(userAddress);
        }
        return new ObjectDataResponse<>(userAddress);
    }

    @ApiOperation(value = "用户删除收件地址", notes = "删除收件地址")
    @RequestMapping(value = "/users/address/{addressId}", method = RequestMethod.DELETE, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse addressDelete(@PathVariable Long addressId) {
        AddressDeleteRequest request = new AddressDeleteRequest();
        request.setId(addressId);
        userAddressService.addressDelete(request);
        return new ObjectDataResponse<>(null);
    }

    @ApiOperation(value = "收件地址详情", notes = "")
    @RequestMapping(value = "/users/address/detail/{addressId}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<CrmUserAddress> userAddressDetail(@PathVariable Long addressId) {
        final CrmUserAddress defaultAddress = userAddressService.queryUserAddressDetail(addressId);
        return new ObjectDataResponse<>(defaultAddress);
    }

    @ApiOperation(value = "设置默认地址", notes = "设置默认地址")
    @RequestMapping(value = "/users/address/default/{addressId}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<CrmUserAddress> setDefaultAddress(@PathVariable Long addressId) {
        CrmUserAddress userAddress = userAddressService.find(addressId);
        userAddressService.clearDefault(userAddress.getUserId());
        userAddress.setIsDefault(1);
        userAddressService.updateNonNullProperties(userAddress);
        return new ObjectDataResponse<>(userAddress);
    }

}
