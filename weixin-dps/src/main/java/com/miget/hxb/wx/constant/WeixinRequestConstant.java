package com.miget.hxb.wx.constant;

public class WeixinRequestConstant {

	/**
	 * 发送红包请求地址
	 */
	public static final String SEND_REDPACK = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	/**
	 * 企业支付到零钱
	 */
	public static final String PROMOTION_TRANSFERS = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	/**
	 * 企业支付到银行卡
	 */
	public static final String PAY_BANK = "https://api.mch.weixin.qq.com/mmpaysptrans/pay_bank";
	/**
	 * 发送客服消息
	 */
	public static final String SEND_CUSTOM_MSG ="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	/**
	 * 发送模板消息
	 */
	public static final String SEND_TEMPLET_MSG ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
}
