package com.miget.hxb.model.request;

public class WxprepayRequest {

	/**
	 * 商家ID
	 */
	private Long businessId;
	/**
	 * 真实ip
	 */
	private String realIp;
	/**
	 * 订单ID
	 */
	private Long orderId;

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getRealIp() {
		return realIp;
	}

	public void setRealIp(String realIp) {
		this.realIp = realIp;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
