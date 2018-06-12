package com.miget.hxb.wx.request;

import java.util.List;

public class WxUserInfoBatchgetRequest {

	/**
	 * 微信openId
	 */
	private List<UserInfoRequest> user_list;

	public List<UserInfoRequest> getUser_list() {
		return user_list;
	}

	public void setUser_list(List<UserInfoRequest> user_list) {
		this.user_list = user_list;
	}
}
