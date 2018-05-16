package com.miget.hxb.controller;

import com.github.pagehelper.Page;
import com.miget.hxb.Delay;
import com.miget.hxb.RandomlyThrowsException;
import com.miget.hxb.Shift;
import com.miget.hxb.domain.CimBusiness;
import com.miget.hxb.domain.CimProduct;
import com.miget.hxb.model.request.PageRequest;
import com.miget.hxb.model.request.ProductAddRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.page.PageInfo;
import com.miget.hxb.service.CimBusinessService;
import com.miget.hxb.service.CimProductService;
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
public class CimProductController {
    @Autowired
    private CimProductService productService;
    @Autowired
    private CimBusinessService businessService;

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "根据商家ID获取产品列表", notes = "")
    @RequestMapping(value = "/products/{businessId}/product", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<CimProduct>> findUser(@PathVariable Long businessId, @Valid PageRequest request) {
        final CimBusiness business = businessService.find(businessId);
        if (business == null) {
            Shift.fatal(StatusCode.BUSINESS_NOT_EXISTS);
        }
        final Page<CimProduct> productList = productService.queryBusinessProductPageList(businessId,request);
        PageInfo<CimProduct> pageInfo = new PageInfo<>(productList);
        return new ObjectDataResponse<>(pageInfo);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "根据商家ID新增产品", notes = "添加新的产品")
    @RequestMapping(value = "/products/{businessId}/product", method = RequestMethod.PUT)
    public ObjectDataResponse<CimProduct> addressAdd(@PathVariable Long businessId, @Valid @RequestBody ProductAddRequest request, BindingResult error) {
        final CimBusiness business = businessService.find(businessId);
        if (business == null) {
            Shift.fatal(StatusCode.BUSINESS_NOT_EXISTS);
        }
        request.setBusinessId(Math.toIntExact(businessId));
        CimProduct product = new CimProduct();
        BeanUtils.copyProperties(request,product);
        productService.persistNonNullProperties(product);
        return new ObjectDataResponse<>(product);
    }

}
