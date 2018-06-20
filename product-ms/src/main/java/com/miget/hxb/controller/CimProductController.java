package com.miget.hxb.controller;

import com.github.pagehelper.Page;
import com.miget.hxb.Shift;
import com.miget.hxb.domain.CimBusiness;
import com.miget.hxb.domain.CimProduct;
import com.miget.hxb.model.request.*;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.model.response.ProductDetailResponse;
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
import java.util.Map;

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

    @ApiOperation(value = "根据商家ID获取产品列表", notes = "产品列表")
    @RequestMapping(value = "/products/{businessId}/product", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<CimProduct>> productPageList(@PathVariable Long businessId, @Valid @ModelAttribute PageRequest request) {
        final CimBusiness business = businessService.find(businessId);
        if (business == null) {
            Shift.fatal(StatusCode.BUSINESS_NOT_EXISTS);
        }
        final Page<CimProduct> productList = productService.queryBusinessProductPageList(businessId,request);
        PageInfo<CimProduct> pageInfo = new PageInfo<>(productList);
        return new ObjectDataResponse<>(pageInfo);
    }

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

    @ApiOperation(value = "订单取消增加产品库存", notes = "更新产品库存")
    @RequestMapping(value = "/products/cancle/inventory", method = RequestMethod.POST)
    public ObjectDataResponse<Integer> orderCancel(@RequestBody List<OrderCancelRequest> orderCancelRequests, BindingResult error){
        Integer result = productService.orderCancel(orderCancelRequests);
        return new ObjectDataResponse<>(result);
    }

    @ApiOperation(value = "预订单减少产品库存", notes = "减少产品库存")
    @RequestMapping(value = "/products/preorder/inventory", method = RequestMethod.POST)
    public ObjectDataResponse<Map<Integer,CimProduct>> orderProductInventory(@RequestBody List<PlaceOrderItemRequest> preOrderRequests, BindingResult error){
        Map<Integer,CimProduct> result = productService.orderProductInventory(preOrderRequests);
        return new ObjectDataResponse<>(result);
    }

    @ApiOperation(value = "批量按产品ID查询产品", notes = "查询产品")
    @RequestMapping(value = "/products", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<Map<Integer, CimProduct>> findProducts(@RequestBody List<Integer> productIds){
        Map<Integer,CimProduct> result = productService.findProducts(productIds);
        return new ObjectDataResponse<>(result);
    }

    @ApiOperation(value = "商家打折产品列表", notes = "产品列表")
    @RequestMapping(value = "/products/{businessId}/onSale", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<CimProduct>> productOnSalePageList(@PathVariable Long businessId, @Valid @ModelAttribute PageRequest request) {
        final CimBusiness business = businessService.find(businessId);
        if (business == null) {
            Shift.fatal(StatusCode.BUSINESS_NOT_EXISTS);
        }
        final Page<CimProduct> productList = productService.queryBusinessOnSaleProductPageList(businessId,request);
        PageInfo<CimProduct> pageInfo = new PageInfo<>(productList);
        return new ObjectDataResponse<>(pageInfo);
    }

    @ApiOperation(value = "热门产品列表", notes = "产品列表")
    @RequestMapping(value = "/products/{businessId}/hot", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<CimProduct>> hotProductPageList(@PathVariable Long businessId, @Valid @ModelAttribute PageRequest request) {
        final CimBusiness business = businessService.find(businessId);
        if (business == null) {
            Shift.fatal(StatusCode.BUSINESS_NOT_EXISTS);
        }
        final Page<CimProduct> productList = productService.queryHotProductPageList(businessId,request);
        PageInfo<CimProduct> pageInfo = new PageInfo<>(productList);
        return new ObjectDataResponse<>(pageInfo);
    }

    @ApiOperation(value = "产品分类列表", notes = "产品列表")
    @RequestMapping(value = "/products/catecategoty/product", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<CimProduct>> productCatePageList(@Valid @ModelAttribute ProductCatePageRequest request) {
        final Page<CimProduct> productList = productService.queryProductCatePageList(request);
        PageInfo<CimProduct> pageInfo = new PageInfo<>(productList);
        return new ObjectDataResponse<>(pageInfo);
    }

    @ApiOperation(value = "产品详情", notes = "产品详情")
    @RequestMapping(value = "/products/{productId}/detail", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<ProductDetailResponse> productDetail(@PathVariable Long productId) {
        ProductDetailResponse productDetail = productService.productDetail(productId);
        return new ObjectDataResponse<>(productDetail);
    }

}
