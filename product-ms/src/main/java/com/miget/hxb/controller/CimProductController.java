package com.miget.hxb.controller;

import com.github.pagehelper.Page;
import com.miget.hxb.Delay;
import com.miget.hxb.RandomlyThrowsException;
import com.miget.hxb.Shift;
import com.miget.hxb.domain.CimBusiness;
import com.miget.hxb.domain.CimProduct;
import com.miget.hxb.model.request.OrderCancelRequest;
import com.miget.hxb.model.request.PageRequest;
import com.miget.hxb.model.request.ProductAddRequest;
import com.miget.hxb.model.request.ProductUpdateRequest;
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
import java.util.List;

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
    @ApiOperation(value = "根据商家ID获取产品列表", notes = "产品列表")
    @RequestMapping(value = "/products/{businessId}/product", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<CimProduct>> productPageList(@PathVariable Long businessId, @Valid PageRequest request) {
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
    public ObjectDataResponse<CimProduct> productAdd(@PathVariable Long businessId, @Valid @RequestBody ProductAddRequest request, BindingResult error) {
        final CimBusiness business = businessService.find(businessId);
        if (business == null) {
            Shift.fatal(StatusCode.BUSINESS_NOT_EXISTS);
        }
        request.setBusinessId(Math.toIntExact(businessId));
        CimProduct product = new CimProduct();
        BeanUtils.copyProperties(request,product);
        product.setOperator("system");
        productService.persistNonNullProperties(product);
        return new ObjectDataResponse<>(product);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "根据商家ID更新产品", notes = "更新产品")
    @RequestMapping(value = "/products/{businessId}/product", method = RequestMethod.POST)
    public ObjectDataResponse productUpdate(@PathVariable Long businessId, @Valid @RequestBody ProductUpdateRequest request, BindingResult error) {
        final CimBusiness business = businessService.find(businessId);
        if (business == null) {
            Shift.fatal(StatusCode.BUSINESS_NOT_EXISTS);
        }
        request.setBusinessId(Math.toIntExact(businessId));
        CimProduct product = new CimProduct();
        BeanUtils.copyProperties(request,product);
        productService.updateNonNullProperties(product);
        return new ObjectDataResponse<>(null);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "订单取消增加产品库存", notes = "更新产品库存")
    @RequestMapping(value = "/products/inventory", method = RequestMethod.POST)
    ObjectDataResponse<Integer> orderCancel(@RequestBody List<OrderCancelRequest> orderCancelRequests){
        Integer result = productService.orderCancel(orderCancelRequests);
        return new ObjectDataResponse<>(result);
    }

}
