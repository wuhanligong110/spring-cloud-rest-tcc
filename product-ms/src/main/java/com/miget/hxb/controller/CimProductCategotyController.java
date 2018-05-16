package com.miget.hxb.controller;

import com.github.pagehelper.Page;
import com.miget.hxb.Delay;
import com.miget.hxb.RandomlyThrowsException;
import com.miget.hxb.Shift;
import com.miget.hxb.domain.CimBusiness;
import com.miget.hxb.domain.CimProduct;
import com.miget.hxb.domain.CimProductCategoty;
import com.miget.hxb.model.request.*;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.page.PageInfo;
import com.miget.hxb.service.CimBusinessService;
import com.miget.hxb.service.CimProductCategotyService;
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
public class CimProductCategotyController {

    @Autowired
    private CimProductCategotyService productCategotyService;

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "查询产品分类", notes = "产品分类列表")
    @RequestMapping(value = "/products/product/categoty", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<List<CimProductCategoty>> productCategotyList(@RequestParam Integer level) {
        List<CimProductCategoty> categoties = productCategotyService.queryProductCategotyList(level);
        return new ObjectDataResponse<>(categoties);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "新增产品分类", notes = "添加新的产品分类")
    @RequestMapping(value = "/products/product/categoty", method = RequestMethod.PUT)
    public ObjectDataResponse<CimProductCategoty> productCategotyAdd(@Valid @RequestBody ProductCategotyAddRequest request, BindingResult error) {
        CimProductCategoty categoty = new CimProductCategoty();
        BeanUtils.copyProperties(request,categoty);
        categoty.setOperator("system");
        productCategotyService.persistNonNullProperties(categoty);
        return new ObjectDataResponse<>(categoty);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "更新产品分类", notes = "更新产品分类")
    @RequestMapping(value = "/products/product/categoty", method = RequestMethod.POST)
    public ObjectDataResponse productCategotyUpdate(@PathVariable Long businessId, @Valid @RequestBody ProductCategotyUpdateRequest request, BindingResult error) {
        CimProductCategoty categoty = new CimProductCategoty();
        BeanUtils.copyProperties(request,categoty);
        productCategotyService.updateNonNullProperties(categoty);
        return new ObjectDataResponse<>(null);
    }

}
