package com.miget.hxb.controller;

import com.eshop4j.web.service.*;
import com.miget.hxb.service.WeixinService;
import com.miget.hxb.wx.request.WxprepayRequest;
import com.miget.hxb.wx.utils.IpKit;
import com.miget.hxb.wx.utils.WeixinUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("api/wxpay")
public class WeixinPayController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeixinPayController.class);
	
	@Resource
	private WeixinService weixinService;
	@Resource
	private BizCustomerService bizCustomerService;
	@Resource
	private BizProductService bizProductService;
	@Resource
	private BizOrderItemService bizOrderItemService;
	@Resource
	private BizOrderService bizOrderService;
    
    /**
     * 统一下单接口
     * type 0-需生成微信预支付订单  1-无需生成微信预支付订单  默认0
     */
    @RequestMapping(value = "prepay")
    @ResponseBody
    public Map<String, String> wxprepay(HttpServletRequest request, HttpServletResponse response, @RequestBody WxprepayRequest wxprepayRequest) {
    	LOGGER.debug("开始调用微信统一下单接口");
    	Map<String, String> returnParams = new HashMap<String, String>();
    	String openId = WeixinUtil.getUserOpenId(request,response);
    	if(StringUtils.isNotBlank(openId)){		
    		wxprepayRequest.setOpenId(openId);
    		wxprepayRequest.setRealIp(IpKit.getRealIp(request));
    		returnParams = weixinService.wxprepay(wxprepayRequest);
    	}
    	return returnParams;
    }

    /**
     * 支付成功微信通知
     */
    @RequestMapping("paynotify")
    public void payNotify(HttpServletRequest request, HttpServletResponse response) {
    	LOGGER.debug("支付成功微信通知");
    	weixinService.payNotify(request,response);
    }
    
    /**
     * 微信提现
     */
    @RequestMapping("enchashment")
    @ResponseBody
    public String enchashment(HttpServletRequest request, HttpServletResponse response){
    	LOGGER.debug("开始微信提现");
    	return weixinService.enchashment(request,response);
    }

}
