package com.miget.hxb.controller;

import com.github.pagehelper.Page;
import com.miget.hxb.domain.CimBusiness;
import com.miget.hxb.model.request.PageRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.page.PageInfo;
import com.miget.hxb.service.CimBusinessService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author hxb
 */
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class CimBusinessController {

    @Autowired
    private CimBusinessService businessService;

    @ApiOperation(value = "推荐商家信息", notes = "商家信息")
    @RequestMapping(value = "/businesses/business", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<PageInfo<CimBusiness>> businessPageList(@Valid @ModelAttribute PageRequest request) {
        final Page<CimBusiness> businessList = businessService.findRecommend(request);
        PageInfo<CimBusiness> pageInfo = new PageInfo<>(businessList);
        return new ObjectDataResponse<>(pageInfo);
    }

}
