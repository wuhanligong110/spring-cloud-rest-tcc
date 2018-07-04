package com.miget.hxb.controller;

import com.miget.hxb.Delay;
import com.miget.hxb.RandomlyThrowsException;
import com.miget.hxb.controller.client.ProductClient;
import com.miget.hxb.domain.SysConfig;
import com.miget.hxb.model.request.ConfigRequest;
import com.miget.hxb.model.request.PrepayRequest;
import com.miget.hxb.model.request.WithdrawRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.sender.RabbitSender;
import com.miget.hxb.service.PayService;
import com.miget.hxb.util.MQConstants;
import com.miget.hxb.util.RabbitMetaMessage;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
    @Resource
    private ProductClient productClient;

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

    @ApiOperation(value = "下单", notes = "生成预订单")
    @RequestMapping(value = "/pay/prepay", method = RequestMethod.POST)
    public Map<String, String> placeOrder(Long userId,@RequestBody PrepayRequest prepayRequest) {
        logger.debug("开始调用统一下单接口");
        prepayRequest.setUserId(userId);
        Map<String, String> returnParams = new HashMap<String, String>();
        returnParams = payService.placeOrder(prepayRequest);
        return returnParams;
    }

    @ApiOperation(value = "提现", notes = "提现")
    @RequestMapping(value = "/pay/{businessId}/enchashment", method = RequestMethod.POST)
    public ObjectDataResponse enchashment(@PathVariable Long businessId,Long userId,@RequestBody WithdrawRequest request) {
        payService.enchashment(businessId,userId,request);
        return new ObjectDataResponse<>(null);
    }

    @ApiOperation(value = "测试获取配置", notes = "测试获取配置")
    @RequestMapping(value = "/pay/config/list", method = RequestMethod.POST)
    public ObjectDataResponse configList() {
        ConfigRequest request = new ConfigRequest();
        request.setBusinessId(1L);
        request.setConfigType("enchashment_config");
        ObjectDataResponse<List<SysConfig>> listData = productClient.sysConfigList(request);
        return new ObjectDataResponse<>(listData.getData());
    }

}
