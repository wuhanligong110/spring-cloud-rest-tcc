package com.miget.hxb.controller;

import com.miget.hxb.controller.client.ProductClient;
import com.miget.hxb.model.request.WxprepayRequest;
import com.miget.hxb.service.WeixinService;
import com.miget.hxb.wx.utils.IpKit;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("api/v1")
public class WeixinPayController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeixinPayController.class);

	@Resource
	private WeixinService weixinService;

    @Resource
    private ProductClient productClient;

    /**
     * 统一下单接口
     * type 0-需生成微信预支付订单  1-无需生成微信预支付订单  默认0
     */
    @RequestMapping(value = "/wxpay/prepay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "下单", notes = "生成预订单")
    public Map<String, String> wxprepay(HttpServletRequest request, HttpServletResponse response, @RequestBody WxprepayRequest wxprepayRequest) {
    	LOGGER.debug("开始调用微信统一下单接口");
    	Map<String, String> returnParams = new HashMap<String, String>();
        wxprepayRequest.setRealIp(IpKit.getRealIp(request));
        returnParams = weixinService.wxprepay(request,response,wxprepayRequest);
    	return returnParams;
    }

    /**
     * 支付成功微信通知
     */
    @RequestMapping("/wxpay/paynotify")
    @ApiOperation(value = "支付成功微信通知", notes = "微信通知")
    public void payNotify(HttpServletRequest request, HttpServletResponse response) {
    	LOGGER.debug("支付成功微信通知");
    	weixinService.payNotify(request,response);
    }

    /**
     * 微信提现
     */
    /*@RequestMapping("/wxpay/enchashment")
    @ResponseBody
    public String enchashment(HttpServletRequest request, HttpServletResponse response){
    	LOGGER.debug("开始微信提现");
    	return weixinService.enchashment(request,response);
    }*/

}
