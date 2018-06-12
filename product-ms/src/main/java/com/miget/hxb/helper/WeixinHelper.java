package com.miget.hxb.helper;

import com.alibaba.fastjson.JSONObject;
import com.miget.hxb.domain.WxAccessToken;
import com.miget.hxb.service.WxAccessTokenService;
import com.miget.hxb.util.DateUtils;
import com.miget.hxb.util.HttpRequestClient;
import com.miget.hxb.wx.utils.WeixinUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeixinHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeixinHelper.class);
    
    @Autowired
    private WxAccessTokenService wxAccessTokenService;
    
	/** 
     * 获取access_token
     * @param appid 凭证 
     * @param appsecret 密钥 
     * @param ifForce 是否强制刷新
     * @return 
     */  
    public String getAccessToken(Long businessId,String appid, String appsecret,Boolean ifForce) {
    	
    	String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    	
    	String accessToken = null;
    	
    	WxAccessToken wxAccessToken = wxAccessTokenService.selectNewAccessToken(businessId);
    	if(wxAccessToken != null &&  (System.currentTimeMillis() - wxAccessToken.getCrtTime())/1000 < wxAccessToken.getExpiresIn() && !ifForce){
    		accessToken = wxAccessToken.getAccessToken();
    	} else {
    		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
    		try {
    			String responseData = HttpRequestClient.invokeGet(requestUrl, null, null, null);
    			if(WeixinUtil.checkWXResult(responseData)){
    				JSONObject jsonObject = JSONObject.parseObject(responseData);
    				// 如果请求成功  
    				if (null != jsonObject) {
    					accessToken = jsonObject.getString("access_token");
    					if(StringUtils.isNotEmpty(accessToken)){				
    						//写入微信access_token表
    						WxAccessToken wxAccessTokenN = new WxAccessToken();
							wxAccessTokenN.setBusinessId(businessId);
    						wxAccessTokenN.setAccessToken(accessToken);
    						wxAccessTokenN.setCreateDate(DateUtils.getNow());
    						wxAccessTokenN.setCrtTime(System.currentTimeMillis());
    						wxAccessTokenN.setExpiresIn((long) jsonObject.getIntValue("expires_in"));
    						wxAccessTokenService.persist(wxAccessTokenN);
    					}
    				}	
    			}
    		} catch (Exception e) {
    			// 获取token失败  
    			LOGGER.info("根据appid 凭证和appsecret 密钥 获取access_token失败",e);                
    		}
    	}
        return accessToken;  
    }
}
