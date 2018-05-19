package com.miget.hxb.wx.request;

import com.eshop4j.web.model.ProductOrder;

import java.util.List;

public class WxprepayRequest {

	/**
	 * 微信openId
	 */
	private String openId;
	/**
	 * 微信code
	 */
	private String code;
	/**
	 * 订单列表
	 */
	private List<ProductOrder> productOrderList;
	/**
	 * 真实ip
	 */
	private String realIp;
	/**
	 * 预支付类型   0-需生成微信预支付订单   1-无需生成微信预支付订单
	 */
	private Integer type =0;
	/**
	 * 前端计算总金额
	 */
	private Integer total =0;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<ProductOrder> getProductOrderList() {
		return productOrderList;
	}

	public void setProductOrderList(List<ProductOrder> productOrderList) {
		this.productOrderList = productOrderList;
	}

	public String getRealIp() {
		return realIp;
	}

	public void setRealIp(String realIp) {
		this.realIp = realIp;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
