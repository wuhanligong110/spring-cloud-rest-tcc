package com.miget.hxb.controller;

import com.miget.hxb.Shift;
import com.miget.hxb.domain.CimBusiness;
import com.miget.hxb.model.request.ConfigRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.service.CimBusinessService;
import com.miget.hxb.service.SysConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hxb
 */
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class SysConfigController {

    @Autowired
    private SysConfigService configService;
    @Autowired
    private CimBusinessService businessService;

    @ApiOperation(value = "根据商家ID获取系统配置", notes = "商家系统配置")
    @RequestMapping(value = "/config/{businessId}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<String> sysConfig(@PathVariable Long businessId, @Valid @ModelAttribute ConfigRequest request) {
        final CimBusiness business = businessService.find(businessId);
        if (business == null) {
            Shift.fatal(StatusCode.BUSINESS_NOT_EXISTS);
        }
        String result = configService.getValuesByBnIdAndKey(businessId,request.getConfigKey());
        return new ObjectDataResponse<>(result);
    }

}
