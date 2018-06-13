package com.miget.hxb.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Preconditions;
import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.controller.client.ProductClient;
import com.miget.hxb.domain.SysBusinessWeixinConfig;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.util.HttpRequestClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.miget.hxb.wx.utils.WeixinUtil.checkWXResult;

@Service
public class WeixinService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeixinService.class);

	@Resource
	private ProductClient productClient;

	/**
	 * 获取当前进入微信公众号用户的openid
	 * @param request
	 * @param response
	 * @return
	 */
	public String getUserOpenId(HttpServletRequest request, HttpServletResponse response){
		Object openIdObj = request.getSession().getAttribute("openId");
		Long businessId = (Long) request.getSession().getAttribute("businessId");
		String openId = "";
		if(openIdObj!=null){
			openId = openIdObj.toString();
		}
		if (StringUtils.isEmpty(openId)) {
			openId = (String) request.getParameter("openId");
			if(StringUtils.isEmpty(openId)){
				String code = (String) request.getParameter("code");
				if(StringUtils.isEmpty(code)){
//					String requestURILocall = getRequestUrl(request);
//					LOGGER.info("通过页面重定向获取code,页面当前请求 URL={}",requestURILocall);
//					String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
//					try {
//						requestUrl = requestUrl.replace("APPID",WeixinConstant.APPID).replace("REDIRECT_URI",URLEncoder.encode(requestURILocall,"UTF-8"));
//						LOGGER.info("通过页面重定向获取code,最终重定向 requestUrl={}",requestUrl);
//						response.sendRedirect(requestUrl);
//					} catch (IOException e) {
//						LOGGER.error("获取当前进入微信公众号用户的openid进行页面重定向异常",e);
//					}
					LOGGER.error("获取当前进入微信公众号用户的openid异常,code为null");
				} else {
					SysBusinessWeixinConfig weixinConfig = remoteWeixinConfig(businessId);
					if(weixinConfig != null){
						openId = getUserOpenId(weixinConfig.getAppId(), weixinConfig.getAppSecret(), code);
					}
				}
			}
			request.getSession().setAttribute("openId", openId);
		}
		LOGGER.info("当前进入微信公众号用户的 openId={}",openId);
		if(StringUtils.isEmpty(openId)){
			openId = "oTZdswPIB9w6XtcIOjZx6nAKy7SA";
			LOGGER.info("获取openId为null 直接使用自己的 openId={}",openId);
		}
		return openId;
	}

	/**
	 * 获取openId
	 * @param appid
	 * @param appSecret
	 * @param code
	 * @return
	 * 采用 网页授权获取 access_token API：SnsAccessTokenApi获取
	 */
	private String getUserOpenId(String appid, String appSecret, String code){
		LOGGER.info("根据code获取openId,code={}",code);
		String openId = "";
		try {
			String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
			requestUrl = requestUrl.replace("APPID", appid).replace("SECRET", appSecret).replace("CODE", code);
			String response = HttpRequestClient.invokeGet(requestUrl, null, null, null);
			if(checkWXResult(response)){
				Map<String, Object> resultMap = JSONObject.parseObject(response, new TypeReference<Map<String, Object>>() {});
				if (resultMap.get("openid") != null) {
					openId = resultMap.get("openid").toString();
				}
			}
		} catch (Exception e) {
			LOGGER.error("根据code获取openId异常", e);
		}
		return openId;
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
