package com.miget.hxb.controller;

import com.miget.hxb.dispatcher.EventDispatcher;
import com.miget.hxb.dispatcher.MsgDispatcher;
import com.miget.hxb.wx.utils.MessageUtil;
import com.miget.hxb.wx.utils.SignUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Controller
@RequestMapping("api/v1")
public class WeixinSecurityController {
	
    private static Logger LOGGER = Logger.getLogger(WeixinSecurityController.class);
    
    @Autowired
    private EventDispatcher eventDispatcher;
    @Autowired
    private MsgDispatcher msgDispatcher;

    /**
     * @Description: 微信服务端消息用于接收 get 参数，返回验证参数
     * @param @param request
     * @param @param response
     * @param @param signature
     * @param @param timestamp
     * @param @param nonce
     * @param @param echostr
     * @author liqimoon
     */
    @ApiOperation(value = "验证微信服务器地址", notes = "")
    @RequestMapping(value = "weixin/security", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam(value = "signature", required = true) String signature,
                      @RequestParam(value = "timestamp", required = true) String timestamp,
                      @RequestParam(value = "nonce", required = true) String nonce,
                      @RequestParam(value = "echostr", required = true) String echostr) {
        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                PrintWriter out = response.getWriter();
                out.print(echostr);
                out.close();
            } else {
                LOGGER.info("验证微信服务器地址存在非法请求！");
            }
        } catch (Exception e) {
            LOGGER.error("微信验证服务器地址有效性异常", e);
        }
    }

    // post 方法用于接收微信服务端消息
    @ApiOperation(value = "接收微信服务端消息", notes = "")
    @RequestMapping(value = "weixin/{businessId}/security", method = RequestMethod.POST)
    public void DoPost(@PathVariable Long businessId,HttpServletRequest request, HttpServletResponse response) {
        try{
            Map<String, String> map=MessageUtil.parseXml(request);
            String msgtype=map.get("MsgType");
            String respMessage = "";
            if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgtype)){
            	respMessage = eventDispatcher.processEvent(map,businessId); //进入事件处理
            }else{
            	respMessage = msgDispatcher.processMessage(map,businessId); //进入消息处理
            }
            
            //响应微信端消息
            if(StringUtils.isNotBlank(respMessage)){
            	LOGGER.info("响应微信端消息,respMessage="+respMessage);
            	// 响应消息  
            	PrintWriter out = null;  
            	try {  
            		out = response.getWriter();  
            		out.print(respMessage);  
            	} catch (IOException e) { 
            		LOGGER.error("响应微信服务端消息返回微信异常", e);
            	} finally {  
            		out.close();  
            		out = null;  
            	}
            }
        }catch(Exception e){
            LOGGER.error(e,e);
        }
    }
}