package com.miget.hxb.service;

import com.eshop4j.api.wx.utils.*;
import com.eshop4j.web.model.*;
import com.eshop4j.web.service.*;
import com.eshop4j.xoss.util.CommonBizUtils;
import com.google.common.base.Preconditions;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.controller.client.AccountClient;
import com.miget.hxb.model.CrmUser;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.wx.constant.WeixinConstant;
import com.miget.hxb.wx.helper.WeixinHelper;
import com.miget.hxb.wx.model.WeixinUserBaseInfo;
import com.miget.hxb.wx.utils.WeixinUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WeixinService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeixinService.class);

	@Autowired
	private AccountClient accountClient;

	@Autowired
	private WeixinHelper weixinHelper;

	public CrmUser handleSubscribe(Long businessId,String openid, String parameter) {
		Preconditions.checkNotNull(businessId);
		Preconditions.checkNotNull(openid);
		Preconditions.checkNotNull(parameter);

		LOGGER.info("扫描带参数二维码处理,openid={},parameter={}",openid,parameter);
		
		//根据openid判断用户是否已经存在  如果存在 则不处理  若不存在 则添加新的用户
		final CrmUser user = queryRemoteUserByOpenId(openid);
		if(user == null){
			//获取微信客户信息
			WeixinUserBaseInfo weixinUserBaseInfo = WeixinUtil.getWeixinUserBaseInfo(weixinHelper.getAccessToken(businessId,WeixinConstant.APPID, WeixinConstant.SECRET,false), openid);
			if(weixinUserBaseInfo != null){
				
	        	//发送欢迎消息
	        	if(sysConfigService.getSwitching("need_subscribe_welcome_msg_switch")){
	        		weixinMessageService.sendCustomTextMessage(openid,sysConfigService.getValuesByKey("need_subscribe_welcome_msg"));
	        	}
	        	
				//积分明细
				List<BizCredit> credits = new ArrayList<BizCredit>();
				
				//根据推荐人id查询推荐人祖先ancestor字段
				String ancestor = "0";
				if(!"0".equals(parameter)){
					BizCustomer bizCustomerP = new BizCustomer();
					bizCustomerP.setConsumerId(Integer.parseInt(parameter));
					bizCustomerP = bizCustomerService.selectOne(bizCustomerP);
					if(bizCustomerP != null){
						weixinMessageService.sendCustomTextMessage(bizCustomerP.getWechatOpenid(), "【"+weixinUserBaseInfo.getNickname()+"】通过您的二维码关注了公众号!");
						if(StringUtils.isNotBlank(bizCustomerP.getAncestor()) && !"0".equals(bizCustomerP.getAncestor())){
							ancestor = bizCustomerP.getAncestor() + "|" + parameter;
						} else {
							ancestor = parameter;
						}
					}
				}
				
				//写入用户表
				bizCustomerA.setParentId(Integer.parseInt(parameter));
				bizCustomerA.setAncestor(ancestor);
				bizCustomerA.setWechatOpenid(openid);
				bizCustomerA.setWechatCity(weixinUserBaseInfo.getCity());
				bizCustomerA.setWechatCountry(weixinUserBaseInfo.getCountry());
				bizCustomerA.setWechatGroupid(weixinUserBaseInfo.getGroupid().toString());
				bizCustomerA.setWechatHeadimgurl(weixinUserBaseInfo.getHeadimgurl());
				bizCustomerA.setWechatLanguage(weixinUserBaseInfo.getLanguage());
				bizCustomerA.setStatus(0);
				
				/**
				 * 分享积分
				 * 关注即奖励100购物积分(消费积分)给推荐人，购物积分(消费积分)只能用来做积分商城兑换，积分商城暂无
				 * 关注时送一次抽奖机会
				 */
				if(!"0".equals(parameter)){				
					bizCustomerService.addCustomerCreditByCustomerIdAndCreditType(Integer.parseInt(parameter), 2,10000l,"客户关注");
					credits.add(CommonBizUtils.createCredit(Integer.parseInt(parameter), 2, 10000l, null,"关注即奖励100购物积分(消费积分)给推荐人",new Date()));
				}
				bizCustomerA.setLotteryNum(1);//送抽奖机会
				
				try {
					bizCustomerA.setWechatNickname(URLEncoder.encode(weixinUserBaseInfo.getNickname(), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					LOGGER.info("写入用户表名称编码失败,wechatNickname={}",weixinUserBaseInfo.getNickname());
					e.printStackTrace();
				}
				bizCustomerA.setWechatProvince(weixinUserBaseInfo.getProvince());
				bizCustomerA.setWechatRemark(weixinUserBaseInfo.getRemark());
				bizCustomerA.setWechatSex(weixinUserBaseInfo.getSex());
				bizCustomerA.setWechatSubscribe(weixinUserBaseInfo.getSubscribe());
				bizCustomerA.setWechatSubscribeTime(new Date(weixinUserBaseInfo.getSubscribe_time()*1000));
				bizCustomerA.setWechatUnionid(weixinUserBaseInfo.getUnionid());
				bizCustomerA.setCreateTime(new Date());
				bizCustomerService.insert(bizCustomerA);
				
				//写入积分明细
				if(CollectionUtils.isNotEmpty(credits)){				
					bizCreditService.inserts(credits);
				}
				
				//返回数据时  名称解码
				bizCustomerA.setWechatNickname(weixinUserBaseInfo.getNickname());
			}
		}
		return user;
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

}
