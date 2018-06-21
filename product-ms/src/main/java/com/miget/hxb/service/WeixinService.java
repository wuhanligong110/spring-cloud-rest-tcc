package com.miget.hxb.service;

import com.google.common.base.Preconditions;
import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.controller.client.AccountClient;
import com.miget.hxb.domain.SysBusinessWeixinConfig;
import com.miget.hxb.helper.WeixinHelper;
import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.wx.model.WeixinUserBaseInfo;
import com.miget.hxb.wx.utils.WeixinUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.OffsetDateTime;
import java.util.Date;

@Service
public class WeixinService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeixinService.class);

	@Autowired
	private AccountClient accountClient;

	@Autowired
	private WeixinHelper weixinHelper;

	@Autowired
	private SysConfigService sysConfigService;

	@Autowired
	private WeixinMessageService weixinMessageService;

	@Autowired
	private SysBusinessWeixinConfigService businessWeixinConfigService;

	public CrmUser handleSubscribe(Long businessId,String openid, String parameter) {
		Preconditions.checkNotNull(businessId);
		Preconditions.checkNotNull(openid);
		Preconditions.checkNotNull(parameter);

		LOGGER.info("扫描带参数二维码处理,openid={},parameter={}",openid,parameter);
		
		//根据openid判断用户是否已经存在  如果存在 则不处理  若不存在 则添加新的用户
		CrmUser user = queryRemoteUserByOpenId(openid);
		if(user == null){
			//获取微信客户信息
			SysBusinessWeixinConfig weixinConfig = businessWeixinConfigService.queryBusinessWeixinConfig(businessId);
			if(weixinConfig != null) {
				WeixinUserBaseInfo weixinUserBaseInfo = WeixinUtil.getWeixinUserBaseInfo(weixinHelper.getAccessToken(businessId, weixinConfig.getAppId(), weixinConfig.getAppSecret(), false), openid);

				if (weixinUserBaseInfo != null) {

					//发送欢迎消息
					if (sysConfigService.getSwitching(businessId, "need_subscribe_welcome_msg_switch")) {
						weixinMessageService.sendCustomTextMessage(businessId, openid, sysConfigService.getValuesByBnIdAndKey(businessId, "need_subscribe_welcome_msg"));
					}

					//根据推荐人id查询推荐人祖先ancestor字段
					String ancestor = "0";
					if (!"0".equals(parameter)) {
						final CrmUser parent = findRemoteUser(Long.parseLong(parameter));
						weixinMessageService.sendCustomTextMessage(businessId, parent.getWechatOpenid(), "【" + weixinUserBaseInfo.getNickname() + "】通过您的二维码关注了公众号!");
						if (StringUtils.isNotBlank(parent.getAncestor()) && !"0".equals(parent.getAncestor())) {
							ancestor = parent.getAncestor() + "|" + parameter;
						} else {
							ancestor = parameter;
						}
					}

					//写入用户表
					user = new CrmUser();
					user.setBusinessId(Math.toIntExact(businessId));
					user.setParentId(Integer.parseInt(parameter));
					user.setAncestor(ancestor);
					user.setWechatOpenid(openid);
					user.setWechatCity(weixinUserBaseInfo.getCity());
					user.setWechatCountry(weixinUserBaseInfo.getCountry());
					user.setWechatGroupid(weixinUserBaseInfo.getGroupid().toString());
					user.setWechatHeadimgurl(weixinUserBaseInfo.getHeadimgurl());
					user.setWechatLanguage(weixinUserBaseInfo.getLanguage());
					user.setStatus(1);

					try {
						user.setWechatNickname(URLEncoder.encode(weixinUserBaseInfo.getNickname(), "utf-8"));
					} catch (UnsupportedEncodingException e) {
						LOGGER.info("写入用户表名称编码失败,wechatNickname={}", weixinUserBaseInfo.getNickname());
						e.printStackTrace();
					}
					user.setWechatProvince(weixinUserBaseInfo.getProvince());
					user.setWechatRemark(weixinUserBaseInfo.getRemark());
					user.setWechatSex(weixinUserBaseInfo.getSex());
					user.setWechatSubscribe(weixinUserBaseInfo.getSubscribe());
					user.setWechatSubscribeTime(new Date(weixinUserBaseInfo.getSubscribe_time() * 1000));
					user.setWechatUnionid(weixinUserBaseInfo.getUnionid());
					user.setCreateTime(OffsetDateTime.now());
					weixinRemoteRegister(user);

					//返回数据时  名称解码
					user.setWechatNickname(weixinUserBaseInfo.getNickname());
				}
			}
		}
		return user;
	}

	private Integer weixinRemoteRegister(CrmUser user) {
		Preconditions.checkNotNull(user);
		final Integer result = accountClient.weixinRemoteRegister(user).getData();
		if (result < 1) {
			Shift.fatal(StatusCode.USER_REGISTER_FAIL);
		}
		return result;
	}

	private CrmUser queryRemoteUserByOpenId(String openid) {
		Preconditions.checkNotNull(openid);
		ObjectDataResponse<CrmUser> userObjectDataResponse = accountClient.queryRemoteUserByOpenId(openid);
		if(userObjectDataResponse.getCode() == StatusCode.USER_NOT_EXISTS.code()){
			return null;
		}
		final CrmUser user = userObjectDataResponse.getData();
		return user;
	}

	private CrmUser findRemoteUser(Long userId) {
		Preconditions.checkNotNull(userId);
		final CrmUser user = accountClient.findUser(userId).getData();
		if (user == null) {
			Shift.fatal(StatusCode.USER_NOT_EXISTS);
		}
		return user;
	}

}
