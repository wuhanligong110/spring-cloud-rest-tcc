package com.miget.hxb.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.controller.client.AccountClient;
import com.miget.hxb.domain.SysBusinessWeixinConfig;
import com.miget.hxb.helper.WeixinHelper;
import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.util.DateUtils;
import com.miget.hxb.wx.custommsg.CustomTextMessage;
import com.miget.hxb.wx.custommsg.content.TextContent;
import com.miget.hxb.wx.message.resp.*;
import com.miget.hxb.wx.model.TemplateData;
import com.miget.hxb.wx.utils.MessageUtil;
import com.miget.hxb.wx.utils.WeixinUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class WeixinMessageService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeixinMessageService.class);
	private static ExecutorService executorService = Executors.newFixedThreadPool(30);

	@Autowired
	private AccountClient accountClient;
	@Autowired
	private WeixinHelper weixinHelper;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private SysBusinessWeixinConfigService businessWeixinConfigService;

	public void sendTempletMsg(final Long businessId, final Long userId,final String type,final String point,final String from,final String remark,final String accessToken) {
		if(sysConfigService.getSwitching(businessId,"wx_sendTempletMsg")){
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					LOGGER.info("开始发送微信模板消息");
					final CrmUser user = findRemoteUser(userId);
					if(user != null){
						String wechatOpenid = user.getWechatOpenid();
						SysBusinessWeixinConfig weixinConfig = businessWeixinConfigService.queryBusinessWeixinConfig(businessId);
						if(weixinConfig != null) {
							String jsonData = TemplateData.New()
									.setTouser(wechatOpenid)
									.setTemplate_id(weixinConfig.getTempletType())
									.setTopcolor("#173177")
									.setUrl(weixinConfig.getIndexPageUrl())
									.add("first", "尊敬的客户【" + user.getUserId() + "】,您好:", "#173177")
									.add("time", DateUtils.getNow("yyyy-MM-dd HH:mm:ss"), "#173177")
									.add("type", type, "#173177")
									.add("Point", point, "#173177")
									.add("From", from, "#173177")
									.add("remark", remark == null ? "感谢您的支持!" : remark, "#173177")
									.build();
							//	String accessToken = "6-lCmfSQyrPRGtoeewM8_uvF0V7pgG_yA6QTbTq-oCWlpiogSr3Uai23rn-JoGyNLIYWebKXhmb3K0F9mdS6DEU5JHw9tpBuA6gyFiVMqSIR8x9zoXeOOPyNAhHKQdD9UJMeAIAODG";
							if (StringUtils.isEmpty(accessToken)) {
								WeixinUtil.sendTempletMsg(weixinHelper.getAccessToken(businessId, weixinConfig.getAppId(), weixinConfig.getAppSecret(), false), jsonData);
							} else {
								WeixinUtil.sendTempletMsg(accessToken, jsonData);
							}
						}
					}
				}
			});
		} else {
			LOGGER.info("微信模板消息开关关闭");	
		}
	}

	public String sendWeixinMessage(Long businessId, String openid,String parameter) {
		
		//发送微信图文消息
		NewsMessage newmsg=new NewsMessage();
		newmsg.setToUserName(openid);
		SysBusinessWeixinConfig weixinConfig = businessWeixinConfigService.queryBusinessWeixinConfig(businessId);
		if(weixinConfig != null) {
			newmsg.setFromUserName(weixinConfig.getFromUserName());
		}
		newmsg.setCreateTime(System.currentTimeMillis());
		newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		
		String wechatImgmsgDescription = null;
		if("0".equals(parameter)){
			wechatImgmsgDescription = sysConfigService.getValuesByBnIdAndKey(businessId,"wechat_notsubscribe_imgmsg_description");
		} else {
			final CrmUser user = findRemoteUser(Long.parseLong(parameter));
			wechatImgmsgDescription = String.format(sysConfigService.getValuesByBnIdAndKey(businessId,"wechat_imgmsg_description"), user != null?user.getWechatNickname():"") ;
		}
		
		Article article=new Article();
		article.setDescription(wechatImgmsgDescription); //图文消息的描述
		article.setPicUrl(sysConfigService.getValuesByBnIdAndKey(businessId,"wechat_imgmsg_picurl")); //图文消息图片地址
		article.setTitle(sysConfigService.getValuesByBnIdAndKey(businessId,"wechat_imgmsg_title"));  //图文消息标题
		article.setUrl(sysConfigService.getValuesByBnIdAndKey(businessId,"wechat_imgmsg_url"));  //图文 url 链接
		List<Article> list=new ArrayList<Article>();
		list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里 list 中加入多个 Article 即可！
		newmsg.setArticleCount(list.size());
		newmsg.setArticles(list);
		LOGGER.info("==============关注发送微信图文消息==============,openid={},newmsg={}",openid,JSONObject.toJSONString(newmsg));
		String returnXmlMessage = MessageUtil.newsMessageToXml(newmsg);
//		LOGGER.info("==============关注发送微信图文消息XML==============");
//		LOGGER.info(returnXmlMessage);
		return returnXmlMessage;
	}

	public String sendWeixinImageMessage(Long businessId,String openid, String parameter) {
		
		ImageMessage imageMessage = new ImageMessage();
		
		//发送微信图片消息
		imageMessage.setToUserName(openid);
		SysBusinessWeixinConfig weixinConfig = businessWeixinConfigService.queryBusinessWeixinConfig(businessId);
		if(weixinConfig != null) {
			imageMessage.setFromUserName(weixinConfig.getFromUserName());
		}
		imageMessage.setCreateTime(System.currentTimeMillis());
		imageMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_Image);
		
		Image image = new Image();
		image.setMediaId(sysConfigService.getValuesByBnIdAndKey(businessId,"weixin_message_mediaid"));
		imageMessage.setImage(image);
		String returnXmlMessage = MessageUtil.imageMessageToXml(imageMessage);
		return returnXmlMessage;
	}

	public String sendWeixinTextMessage(Long businessId,String openid, String text) {
		TextMessage textMessage = new TextMessage();
		
		//发送微信文本消息
		textMessage.setToUserName(openid);
		SysBusinessWeixinConfig weixinConfig = businessWeixinConfigService.queryBusinessWeixinConfig(businessId);
		if(weixinConfig != null) {
			textMessage.setFromUserName(weixinConfig.getFromUserName());
		}
		textMessage.setCreateTime(System.currentTimeMillis());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setContent(text);
		String returnXmlMessage = MessageUtil.textMessageToXml(textMessage);
		return returnXmlMessage;
	}

	public void sendCustomTextMessage(final Long businessId, final String openid, final String content) {
		if(sysConfigService.getSwitching(businessId,"wx_sendCustomMsg")){
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					CustomTextMessage customTextMessage = new CustomTextMessage();
					customTextMessage.setTouser(openid);
					customTextMessage.setMsgtype(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					TextContent textContent = new TextContent();
					textContent.setContent(content);
					customTextMessage.setText(textContent);
					SysBusinessWeixinConfig weixinConfig = businessWeixinConfigService.queryBusinessWeixinConfig(businessId);
					if(weixinConfig != null) {
						WeixinUtil.sendCustomMsg(weixinHelper.getAccessToken(businessId, weixinConfig.getAppId(), weixinConfig.getAppSecret(), false), JSONObject.toJSONString(customTextMessage));
					}
				}
			});
		} else {
			LOGGER.info("微信客服消息开关关闭");	
		}
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
