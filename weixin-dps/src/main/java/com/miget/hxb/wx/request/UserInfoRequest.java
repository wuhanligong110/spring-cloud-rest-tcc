package com.miget.hxb.wx.request;

public class UserInfoRequest {

	/**
	 * 微信openId
	 */
	private String openid;
	/**
	 * 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，默认为zh-CN
	 */
	private String lang = "zh_CN";
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
}
