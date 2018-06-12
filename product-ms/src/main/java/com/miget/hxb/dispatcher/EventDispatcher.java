package com.miget.hxb.dispatcher;

import com.miget.hxb.service.SysConfigService;
import com.miget.hxb.service.WeixinMessageService;
import com.miget.hxb.service.WeixinService;
import com.miget.hxb.util.StringUtils;
import com.miget.hxb.wx.utils.MessageUtil;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * ClassName: EventDispatcher
 * @Description: 事件消息业务分发器
 */
@Component
public class EventDispatcher {
	
	private static Logger LOGGER = LoggerFactory.getLogger(EventDispatcher.class);

	@Autowired
	private WeixinService weixinService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private WeixinMessageService weixinMessageService;
	
    public String processEvent(Map<String, String> map,Long businessId) {
    	
    	LOGGER.info("微信返回参数   map={}", JSONObject.toJSONString(map));
    	
    	String mpid=map.get("ToUserName");   //公众号原始 ID
    	String openid=map.get("FromUserName"); //用户 openid
    	String createtime=map.get("CreateTime");   //消息创建时间 （整型）
    	String msgtype=map.get("MsgType");   //消息类型
    	String event=map.get("Event");   //事件类型
    	String respMessage = "";
    		
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { //subscribe(订阅)/扫描带参数二维码事件(用户未关注)
        	LOGGER.info("==============触发subscribe(订阅)/扫描带参数二维码事件(用户未关注)！");
        	//获取二维码参数
        	String eventkey = map.get("EventKey");
        	LOGGER.info("==============eventkey = {}",eventkey);
        	String parameter = null;
        	if(StringUtils.isNotBlank(eventkey) && eventkey.startsWith("qrscene_")){//带二维码扫描参数关注
        		parameter = eventkey.split("_")[1];
        	} else {//直接关注
        		parameter = "0";
        	}
        	weixinService.handleSubscribe(businessId,openid,parameter);
        	
        	//发送图文消息
        	if(sysConfigService.getSwitching(businessId,"need_subscribe_msg")){
        		Integer messageType = sysConfigService.getIntegerValueByKey(businessId,"weixin_message_type");
        		if(messageType == 0){//图文
        			respMessage = weixinMessageService.sendWeixinMessage(businessId,openid,parameter);
        		} else if (messageType == 1){//图片
        			respMessage = weixinMessageService.sendWeixinImageMessage(businessId,openid,parameter);
        		}
        	}
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { //取消关注事件
            LOGGER.info("==============触发取消关注事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { //扫描带参数二维码事件( 用户已关注时)
            LOGGER.info("==============触发扫描带参数二维码事件( 用户已关注)！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { //位置上报事件
            LOGGER.info("==============触发位置上报事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { //自定义菜单点击事件
            LOGGER.info("==============触发自定义菜单点击事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { //自定义菜单 View 事件
            LOGGER.info("==============触发自定义菜单 View 事件！");
        }

        return respMessage;
    }
}