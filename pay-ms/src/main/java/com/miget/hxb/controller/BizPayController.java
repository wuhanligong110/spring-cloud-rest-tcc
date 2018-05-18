package com.miget.hxb.controller;

import com.miget.hxb.Delay;
import com.miget.hxb.RandomlyThrowsException;
import com.miget.hxb.sender.RabbitSender;
import com.miget.hxb.util.MQConstants;
import com.miget.hxb.util.RabbitMetaMessage;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hxb
 */
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class BizPayController {

    private static final  Logger logger = LoggerFactory.getLogger(BizPayController.class);

    @Autowired
    private RabbitSender rabbitSender;

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

}
