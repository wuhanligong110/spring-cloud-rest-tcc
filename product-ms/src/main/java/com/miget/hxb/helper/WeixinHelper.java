package com.miget.hxb.helper;

import com.alibaba.fastjson.JSONObject;
import com.miget.hxb.domain.SysBusinessWeixinConfig;
import com.miget.hxb.domain.WxAccessToken;
import com.miget.hxb.service.SysBusinessWeixinConfigService;
import com.miget.hxb.service.WxAccessTokenService;
import com.miget.hxb.util.DateUtils;
import com.miget.hxb.util.HttpRequestClient;
import com.miget.hxb.wx.utils.WeixinUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.miget.hxb.wx.utils.RedPackUtil.getRandomNum;

@Component
public class WeixinHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeixinHelper.class);
    
    @Autowired
    private WxAccessTokenService wxAccessTokenService;

	@Autowired
	private SysBusinessWeixinConfigService businessWeixinConfigService;
    
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

	/**
	 * 红包
	 * @param requestUrl
	 * @param xmlData
	 * @return
	 * @throws Exception
	 */
	public String redPackPost(Long businessId,String requestUrl, String xmlData) throws Exception{
		HttpClient httpclient = getWXHttpsclient(businessId);
		return HttpRequestClient.invokePostXML(requestUrl, xmlData, null, httpclient);
	}

	/**
	 * 企业付款
	 * @param requestUrl
	 * @param xmlData
	 * @return
	 * @throws Exception
	 */
	public String transfersPost(Long businessId,String requestUrl,String xmlData) throws Exception{
		HttpClient httpclient = getWXHttpsclient(businessId);
		return HttpRequestClient.invokePostXML(requestUrl, xmlData, null, httpclient);
	}

	/**
	 * 生成商户订单号
	 * @return
	 */
	public String createBillNo(Long businessId){
		//组成： mch_id+yyyymmdd+10位一天内不能重复的数字
		//10位一天内不能重复的数字实现方法如下:
		//因为每个用户绑定了userId,他们的userId不同,加上随机生成的(10-length(userId))可保证这10位数字不一样
		Date dt=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyymmdd");
		String nowTime= df.format(dt);
		int length = 10 ;
		SysBusinessWeixinConfig weixinConfig = businessWeixinConfigService.queryBusinessWeixinConfig(businessId);
		if(weixinConfig != null){
			return weixinConfig.getMchId() + nowTime  + getRandomNum(length);
		}else {
			return null;
		}
	}

	/**
	 * 获取微信Https请求 client
	 * @return
	 */
	public HttpClient getWXHttpsclient(Long businessId){
		HttpClient httpclient = null;
		try {
			KeyStore keyStore  = KeyStore.getInstance("PKCS12");
			SysBusinessWeixinConfig weixinConfig = businessWeixinConfigService.queryBusinessWeixinConfig(businessId);
			if(weixinConfig != null) {
				FileInputStream keyStream = new FileInputStream(new File(weixinConfig.getKeyPath()));
				try {
					keyStore.load(keyStream, weixinConfig.getMchId().toCharArray());
				} finally {
					keyStream.close();
				}

				// Trust own CA and all self-signed certs
				SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, weixinConfig.getMchId().toCharArray()).build();
				// Allow TLSv1 protocol only
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
						sslcontext,
						new String[]{"TLSv1"},
						null,
						SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
				httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			}
		} catch (Exception e) {
			LOGGER.error("获取微信Https请求 client异常", e);
		}
		return httpclient;
	}
}
