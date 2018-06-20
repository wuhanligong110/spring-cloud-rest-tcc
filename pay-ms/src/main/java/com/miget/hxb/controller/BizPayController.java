package com.miget.hxb.controller;

import com.miget.hxb.Delay;
import com.miget.hxb.RandomlyThrowsException;
import com.miget.hxb.model.request.WxprepayRequest;
import com.miget.hxb.sender.RabbitSender;
import com.miget.hxb.service.PayService;
import com.miget.hxb.util.MQConstants;
import com.miget.hxb.util.RabbitMetaMessage;
import com.miget.hxb.wx.utils.IpKit;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hxb
 */
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class BizPayController {

    private static final  Logger logger = LoggerFactory.getLogger(BizPayController.class);

    @Autowired
    private RabbitSender rabbitSender;
    @Resource
    private PayService payService;

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "测试MQ", notes = "测试MQ")
    @RequestMapping(value = "/testmq", method = RequestMethod.POST)
    public String testMessage() throws Exception {
        /** 生成一个发送对象 */
        RabbitMetaMessage  rabbitMetaMessage = new RabbitMetaMessage();
        /**设置交换机 */
        rabbitMetaMessage.setExchange(MQConstants.BUSINESS_EXCHANGE);
        /**指定routing key */
        rabbitMetaMessage.setRoutingKey(MQConstants.BUSINESS_KEY);
        /** 设置需要传递的消息体,可以是任意对象 */
        rabbitMetaMessage.setPayload("the message you want to send");

        //do some biz

        /** 发送消息 */
        rabbitSender.send(rabbitMetaMessage);

        return "sucess";
    }

    @RequestMapping(value = "/pay/prepay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "下单", notes = "生成预订单")
    public Map<String, String> placeOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody WxprepayRequest wxprepayRequest) {
        logger.debug("开始调用统一下单接口");
        Map<String, String> returnParams = new HashMap<String, String>();
        wxprepayRequest.setRealIp(IpKit.getRealIp(request));
        returnParams = payService.placeOrder(request,response,wxprepayRequest);
        return returnParams;
    }

}
