package com.miget.hxb.controller;

import com.miget.hxb.Shift;
import com.miget.hxb.domain.CimBusiness;
import com.miget.hxb.domain.SysBusinessWeixinConfig;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.service.CimBusinessService;
import com.miget.hxb.service.SysBusinessWeixinConfigService;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/v1")
public class SysWeixinConfigController {
	
    private static Logger LOGGER = Logger.getLogger(SysWeixinConfigController.class);

    @Resource
    private SysBusinessWeixinConfigService weixinConfigService;
    @Resource
    private CimBusinessService businessService;

    @ApiOperation(value = "根据商家ID获取微信配置信息", notes = "微信配置")
    @RequestMapping(value = "/weixin/config/{businessId}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<SysBusinessWeixinConfig> weixinConfig(@PathVariable("businessId") Long businessId) {
        final CimBusiness business = businessService.find(businessId);
        if (business == null) {
            Shift.fatal(StatusCode.BUSINESS_NOT_EXISTS);
        }
        SysBusinessWeixinConfig config = weixinConfigService.queryBusinessWeixinConfig(businessId);
        return new ObjectDataResponse<>(config);
    }
}