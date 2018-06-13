package com.miget.hxb.wx.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.miget.hxb.util.HttpRequestClient;
import com.miget.hxb.wx.constant.WeixinConstant;
import com.miget.hxb.wx.constant.WeixinRequestConstant;
import com.miget.hxb.wx.model.WeChatQRCode;
import com.miget.hxb.wx.model.WeixinUserBaseInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class WeixinUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeixinUtil.class);
    
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 随机生成16位字符串
	 * @return
	 */
	public static String createNonceStr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String createTimeStamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

    /** 
     * 创建临时带参数二维码 
     * @param accessToken 
     * @param expireSeconds 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。 
     * @param sceneId  场景Id 
     * @return 
     */  
    public static WeChatQRCode createTemporaryORCode(String accessToken, int expireSeconds ,int sceneId) {  
        WeChatQRCode weChatQRCode = null;  
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
        requestUrl = requestUrl.replace("TOKEN", accessToken);  
        //需要提交的JSON数据  
        String outputStr = "{\"expire_seconds\":%d,\"action_name\":\"QR_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":%d}}}";
        String postJson = String.format(outputStr, expireSeconds, sceneId);
        LOGGER.info("创建临时带参二维码 post请求JSON串,outputStr={}",postJson);  
        //创建临时带参数二维码  
        try {
        	String responseData = HttpRequestClient.invokePostJson(requestUrl, postJson, null, null);
			if(checkWXResult(responseData)){
				JSONObject jsonObject = JSONObject.parseObject(responseData);
                weChatQRCode = new WeChatQRCode();  
                weChatQRCode.setTicket(jsonObject.getString("ticket"));  
                weChatQRCode.setExpire_seconds(jsonObject.getIntValue("expire_seconds"));
                LOGGER.info("创建临时带参二维码成功,ticket="+weChatQRCode.getTicket()+",expire_seconds="+weChatQRCode.getExpire_seconds());
			}
		} catch (Exception e) {
			LOGGER.info("创建临时带参二维码失败",e);  
		}   
        return weChatQRCode;  
    }  
      
    /** 
     * 创建永久二维码 
     * @param accessToken 
     * @param sceneId  场景Id 
     * @param sceneStr  场景IdsceneStr 
     * @return 
     */  
    //数字ID用这个{"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}  
    //或者也可以使用以下POST数据创建字符串形式的二维码参数：  
    //字符ID用这个{"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "hfrunffgha"}}}  
    public static String createPermanentORCode(String accessToken, String sceneStr) {  
        String ticket = null;  
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";  
        requestUrl = requestUrl.replace("TOKEN", accessToken);  
        String outputStr = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\":{\"scene\": {\"scene_str\":%s}}}";
        
        try {
        	String responseData = HttpRequestClient.invokePostJson(requestUrl, String.format(outputStr,sceneStr), null, null);
			if(checkWXResult(responseData)){			
				JSONObject jsonObject = JSONObject.parseObject(responseData);
				ticket = jsonObject.getString("ticket");  
				LOGGER.info("创建永久带参二维码成功,ticket="+ticket);
			}
		} catch (Exception e) {
			LOGGER.info("创建永久带参二维码失败",e);  
		}  
        return ticket;  
    }
    
    /**
     * 根据微信当前用户openid获取微信用户基本信息
     * @param openid
     * @return
     */
    public static WeixinUserBaseInfo getWeixinUserBaseInfo(String accessToken,String openid){
    	WeixinUserBaseInfo weixinUserBaseInfo = null;
		try {
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
			String responseData = HttpRequestClient.invokeGet(requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid), null, null, null);
			if(checkWXResult(responseData)){
				weixinUserBaseInfo = JSONObject.parseObject(responseData, WeixinUserBaseInfo.class);
			}
		} catch (Exception e) {
			LOGGER.error("根据微信当前用户openid获取微信用户基本信息异常,openid={}",openid,e);
		}
    	return weixinUserBaseInfo;
    }
    
    /**
     * 根据微信accessToken批量获取微信用户基本信息
     * @param openid
     * @return
     */
    public static List<WeixinUserBaseInfo> batchGetWeixinUserBaseInfo(String accessToken,Object objectData){
    	List<WeixinUserBaseInfo> weixinUserBaseInfoList = null;
		try {
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
			String responseData = HttpRequestClient.invokePostJson(requestUrl.replace("ACCESS_TOKEN", accessToken), objectData, null, null);
			if(checkWXResult(responseData)){
				weixinUserBaseInfoList = JSONObject.parseArray(JSONObject.parseObject(responseData).getString("user_info_list"), WeixinUserBaseInfo.class);
			}
		} catch (Exception e) {
			LOGGER.error("根据微信accessToken批量获取微信用户基本信息,objectData={}",JSONObject.toJSONString(objectData),e);
		}
    	return weixinUserBaseInfoList;
    }

    /**
     * 发送模板消息
     * @param jsonStr json字符串
     * https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=TOKEN
     */
    public static void sendTempletMsg(String accessToken,String jsonStr) {
    	String requestUrl = WeixinRequestConstant.SEND_TEMPLET_MSG+accessToken;
        try {
        	String responseData = HttpRequestClient.invokePostJson(requestUrl, jsonStr, null, null);
			JSONObject jsonObject = JSONObject.parseObject(responseData);
			if(jsonObject.getString("errcode").equals("0")){
				LOGGER.info("发送模板消息成功,responseData={}",responseData);
			} else {
				LOGGER.info("发送模板消息失败,jsonStr={},responseData={}",jsonStr,responseData);
			}	
		} catch (Exception e) {
			LOGGER.info("发送模板消息失败 jsonStr={}",jsonStr,e);  
		} 
    }
    
    
    /**
     * 发送客服消息
     * @param jsonStr json字符串
     * https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=TOKEN
     */
    public static void sendCustomMsg(String accessToken,String jsonStr) {
    	String requestUrl = WeixinRequestConstant.SEND_CUSTOM_MSG+accessToken;
        try {
        	String responseData = HttpRequestClient.invokePostJson(requestUrl, jsonStr, null, null);
			JSONObject jsonObject = JSONObject.parseObject(responseData);
			if(jsonObject.getString("errcode").equals("0")){
				LOGGER.info("发送客服消息成功,responseData={}",responseData);
			} else {
				LOGGER.info("发送客服消息失败,jsonStr={},responseData={}",jsonStr,responseData);
			}	
		} catch (Exception e) {
			LOGGER.info("发送客服消息失败 jsonStr={}",jsonStr,e);  
		} 
    }
    
    /**
     * 校验微信请求结果
     * @param responseJsonData
     * @return
     */
    public static boolean checkWXResult(String responseJsonData){
    	JSONObject jsonObject = JSONObject.parseObject(responseJsonData);
    	if(jsonObject.get("errcode") != null){
    		return false;
    	} else {
    		return true;
    	}
    }
    
	public static String getRequestUrl(HttpServletRequest request) {
		String requestuRL = request.getScheme() + "://" + request.getServerName();
		if (request.getQueryString() != null) {
			requestuRL += "?" + request.getQueryString();
		}
		return requestuRL;
	}
}
