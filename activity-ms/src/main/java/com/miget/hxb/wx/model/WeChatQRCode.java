package com.miget.hxb.wx.model;

public class WeChatQRCode {

	/**
	 * 获取的二维码ticket
	 */
	private String ticket;
	/**
	 * 该二维码有效时间
	 */
	private Integer expire_seconds;
	/**
	 * 二维码图片解析后的地址
	 */
	private String url;
	
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public Integer getExpire_seconds() {
		return expire_seconds;
	}
	public void setExpire_seconds(Integer expire_seconds) {
		this.expire_seconds = expire_seconds;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
