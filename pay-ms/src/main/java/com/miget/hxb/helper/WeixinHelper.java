package com.miget.hxb.helper;

import com.google.common.base.Preconditions;
import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.controller.client.ProductClient;
import com.miget.hxb.domain.SysBusinessWeixinConfig;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.util.HttpRequestClient;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

	@Resource
	private ProductClient productClient;

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
		SysBusinessWeixinConfig weixinConfig = remoteWeixinConfig(businessId);
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
			SysBusinessWeixinConfig weixinConfig = remoteWeixinConfig(businessId);
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

	private SysBusinessWeixinConfig remoteWeixinConfig(Long businessId){
		Preconditions.checkNotNull(businessId);
		ObjectDataResponse<SysBusinessWeixinConfig> dataResponse =  productClient.weixinConfig(businessId);
		if(dataResponse != null && dataResponse.getData() != null){
			return dataResponse.getData();
		}else{
			Shift.fatal(StatusCode.WEIXIN_CONFIG_NULL);
		}
		return null;
	}

}
