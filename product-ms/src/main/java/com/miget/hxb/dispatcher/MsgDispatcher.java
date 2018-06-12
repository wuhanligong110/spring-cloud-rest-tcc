package com.miget.hxb.dispatcher;

import com.alibaba.fastjson.JSONObject;
import com.miget.hxb.wx.utils.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * ClassName: MsgDispatcher
 * @Description: 消息业务处理分发器
 */
@Component
public class MsgDispatcher {
	
	private static Logger LOGGER = LoggerFactory.getLogger(EventDispatcher.class);
	
    public String processMessage(Map<String, String> map,Long businessId) {
    	
    	LOGGER.info("微信返回参数   map={}",JSONObject.toJSONString(map));
    	
    	String mpid=map.get("ToUserName");   //公众号原始 ID
    	String openid=map.get("FromUserName"); //用户 openid
    	String createtime=map.get("CreateTime");   //消息创建时间 （整型）
    	String msgtype=map.get("MsgType");   //消息类型
    	String content=map.get("Content");   //事件类型
    	String msgid=map.get("MsgId");   //消息id，64位整型
    	
    	String respMessage = "";
    	
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
            LOGGER.info("==============接收到文本消息！");
        }

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
            LOGGER.info("==============接收到图片消息！");
        }

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) { // 链接消息
            LOGGER.info("==============接收到链接消息！");
        }

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) { // 位置消息
            LOGGER.info("==============接收到位置消息！");
        }

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) { // 视频消息
            LOGGER.info("==============接收到视频消息！");
        }

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) { // 语音消息
            LOGGER.info("==============接收到语音消息！");
        }

        return respMessage;
    }
}