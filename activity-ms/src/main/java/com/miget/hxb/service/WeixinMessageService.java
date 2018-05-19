package com.miget.hxb.service;

import com.alibaba.fastjson.JSONObject;
import com.eshop4j.api.wx.constant.WeixinConstant;
import com.eshop4j.api.wx.custommsg.CustomTextMessage;
import com.eshop4j.api.wx.custommsg.content.TextContent;
import com.eshop4j.api.wx.helper.WeixinHelper;
import com.eshop4j.api.wx.message.resp.*;
import com.eshop4j.api.wx.model.TemplateData;
import com.eshop4j.api.wx.utils.MessageUtil;
import com.eshop4j.api.wx.utils.WeixinUtil;
import com.eshop4j.web.model.BizCustomer;
import com.eshop4j.web.service.BizCustomerService;
import com.eshop4j.web.service.SysConfigService;
import com.eshop4j.web.service.WeixinMessageService;
import com.eshop4j.xoss.helper.ConfigHelper;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class WeixinMessageService implements WeixinMessageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeixinMessageService.class);
	private static ExecutorService executorService = Executors.newFixedThreadPool(30);
	
	@Autowired
	private ConfigHelper configHelper;
	@Autowired
	private BizCustomerService bizCustomerService;
	@Autowired
	private WeixinHelper weixinHelper;
	@Autowired
	private SysConfigService sysConfigService;
	
	@Override
	public void sendTempletMsg(final Integer consumerId,final String type,final String point,final String from,final String remark,final String accessToken) {
		if(configHelper.getSwitching("wx_sendTempletMsg")){
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					LOGGER.info("开始发送微信模板消息");				
					BizCustomer bizCustomer = new BizCustomer();
					bizCustomer.setConsumerId(consumerId);
					bizCustomer = bizCustomerService.selectOne(bizCustomer);
					if(bizCustomer != null){
						String wechatOpenid = bizCustomer.getWechatOpenid();
						String jsonData = TemplateData.New()
								.setTouser(wechatOpenid)
								.setTemplate_id(WeixinConstant.TEMPLET_TYPE)
								.setTopcolor("#173177")
								.setUrl(WeixinConstant.INDEX_PAGE_URL)
								.add("first", "尊敬的客户【"+bizCustomer.getConsumerId()+"】,您好:", "#173177")
								.add("time",DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), "#173177")
								.add("type", type, "#173177")
								.add("Point", point, "#173177")
								.add("From", from, "#173177")
								.add("remark", remark == null?"感谢您的支持!":remark, "#173177")
								.build();
						//	String accessToken = "6-lCmfSQyrPRGtoeewM8_uvF0V7pgG_yA6QTbTq-oCWlpiogSr3Uai23rn-JoGyNLIYWebKXhmb3K0F9mdS6DEU5JHw9tpBuA6gyFiVMqSIR8x9zoXeOOPyNAhHKQdD9UJMeAIAODG";
						if(StringUtils.isEmpty(accessToken)){
							WeixinUtil.sendTempletMsg(weixinHelper.getAccessToken(WeixinConstant.APPID, WeixinConstant.SECRET,false), jsonData);
						} else {							
							WeixinUtil.sendTempletMsg(accessToken, jsonData);
						}
					}
				}
			});
		} else {
			LOGGER.info("微信模板消息开关关闭");	
		}
	}

	@Override
	public String sendWeixinMessage(String openid,String parameter) {
		
		//发送微信图文消息
		NewsMessage newmsg=new NewsMessage();
		newmsg.setToUserName(openid);
		newmsg.setFromUserName(WeixinConstant.FROM_USER_NAME);
		newmsg.setCreateTime(new Date().getTime());
		newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		
		String wechatImgmsgDescription = null;
		if("0".equals(parameter)){
			wechatImgmsgDescription = sysConfigService.getValuesByKey("wechat_notsubscribe_imgmsg_description");
		} else {
			BizCustomer bizCustomer = bizCustomerService.queryCustomerByCustomerId(Integer.parseInt(parameter));
			wechatImgmsgDescription = String.format(sysConfigService.getValuesByKey("wechat_imgmsg_description"), bizCustomer != null?bizCustomer.getWechatNickname():"") ;
		}
		
		Article article=new Article();
		article.setDescription(wechatImgmsgDescription); //图文消息的描述
		article.setPicUrl(sysConfigService.getValuesByKey("wechat_imgmsg_picurl")); //图文消息图片地址
		article.setTitle(sysConfigService.getValuesByKey("wechat_imgmsg_title"));  //图文消息标题
		article.setUrl(sysConfigService.getValuesByKey("wechat_imgmsg_url"));  //图文 url 链接
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
	
	@Override
	public String sendWeixinImageMessage(String openid, String parameter) {
		
		ImageMessage imageMessage = new ImageMessage();
		
		//发送微信图片消息
		imageMessage.setToUserName(openid);
		imageMessage.setFromUserName(WeixinConstant.FROM_USER_NAME);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_Image);
		
		Image image = new Image();
		image.setMediaId(sysConfigService.getValuesByKey("weixin_message_mediaid"));
		imageMessage.setImage(image);
		String returnXmlMessage = MessageUtil.imageMessageToXml(imageMessage);
		return returnXmlMessage;
	}
	
	@Override
	public String sendWeixinTextMessage(String openid, String text) {
		TextMessage textMessage = new TextMessage();
		
		//发送微信文本消息
		textMessage.setToUserName(openid);
		textMessage.setFromUserName(WeixinConstant.FROM_USER_NAME);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setContent(text);
		String returnXmlMessage = MessageUtil.textMessageToXml(textMessage);
		return returnXmlMessage;
	}

	@Override
	public void sendCustomTextMessage(final String openid, final String content) {
		if(configHelper.getSwitching("wx_sendCustomMsg")){
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					CustomTextMessage customTextMessage = new CustomTextMessage();
					customTextMessage.setTouser(openid);
					customTextMessage.setMsgtype(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					TextContent textContent = new TextContent();
					textContent.setContent(content);
					customTextMessage.setText(textContent); 
				    WeixinUtil.sendCustomMsg(weixinHelper.getAccessToken(WeixinConstant.APPID, WeixinConstant.SECRET,false), JSONObject.toJSONString(customTextMessage));					
				}
			});
		} else {
			LOGGER.info("微信客服消息开关关闭");	
		}
	}
}
