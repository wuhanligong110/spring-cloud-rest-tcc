package com.miget.hxb.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.controller.client.AccountClient;
import com.miget.hxb.controller.client.OrderClient;
import com.miget.hxb.controller.client.ProductClient;
import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.domain.SysBusinessWeixinConfig;
import com.miget.hxb.model.request.PaymentRequest;
import com.miget.hxb.model.request.WxprepayRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.model.response.OrderDetailResponse;
import com.miget.hxb.model.response.OrderItemResponse;
import com.miget.hxb.util.HttpRequestClient;
import com.miget.hxb.util.RequestUtil;
import com.miget.hxb.wx.PaymentApi;
import com.miget.hxb.wx.utils.PaymentKit;
import com.miget.hxb.wx.utils.WeixinUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.miget.hxb.wx.utils.WeixinUtil.checkWXResult;

@Service
public class WeixinService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeixinService.class);

	@Resource
	private AccountClient accountClient;
	@Resource
	private ProductClient productClient;
	@Resource
	private OrderClient orderClient;

	public Map<String, String> wxprepay(HttpServletRequest request, HttpServletResponse response,WxprepayRequest wxprepayRequest) {

		Long businessId = wxprepayRequest.getBusinessId();

		Map<String, String> returnParams = new HashMap<String, String>();

		LOGGER.info("调用微信统一下单接口请求参数,wxprepayRequest={}", JSONObject.toJSONString(wxprepayRequest));

		//订单id  和 openid

		String openId = getUserOpenId(request,response);

		String productNameTotal = "";

		OrderDetailResponse detailResponse = remoteOrderDetail(wxprepayRequest.getOrderId());
		Preconditions.checkNotNull(detailResponse);
		String out_trade_no = detailResponse.getOutTradeNo();
		Long total_fee = detailResponse.getAmount();

		for (OrderItemResponse orderItem : detailResponse.getOrderItems()) {
		    productNameTotal += "["+orderItem.getProductName()+"]";//拼接支付订单名称
		}

		SysBusinessWeixinConfig weixinConfig = remoteWeixinConfig(businessId);
		if(weixinConfig != null) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("appid", weixinConfig.getAppId());
			params.put("mch_id", weixinConfig.getMchId());
			params.put("trade_type", PaymentApi.TradeType.JSAPI.name());
			params.put("nonce_str", WeixinUtil.createNonceStr());
			params.put("notify_url", weixinConfig.getNotifyUrl());
			params.put("spbill_create_ip", wxprepayRequest.getRealIp());
			params.put("body", productNameTotal);
			params.put("out_trade_no", out_trade_no);
			params.put("total_fee", total_fee.toString());
			params.put("openid", openId);
			String sign = PaymentKit.createSign(params, weixinConfig.getPayKey());
			params.put("sign", sign);

			LOGGER.info("转换微信统一下单接口请求参数,params={}", JSONObject.toJSONString(params));
			// 调用统一下单接口   若下单接口成功，获取预付商品id
			Map<String, String> result = PaymentKit.xmlToMap(PaymentApi.pushOrder(params));
			String prepay_id = "";
			String return_code = result.get("return_code");
			if ("SUCCESS".equals(return_code)) {
				prepay_id = result.get("prepay_id");
			}
			returnParams.put("appId", weixinConfig.getAppId());
			returnParams.put("timeStamp", WeixinUtil.createTimeStamp());
			returnParams.put("nonceStr", WeixinUtil.createNonceStr());
			returnParams.put("package", "prepay_id=" + prepay_id);
			returnParams.put("signType", "MD5");
			String paySign = PaymentKit.createSign(returnParams, weixinConfig.getPayKey());
			returnParams.put("paySign", paySign);
			returnParams.put("prepayId", prepay_id);
			LOGGER.info("微信统一下单【需生成微信预支付订单】成功返回页面参数,returnParams={}", JSONObject.toJSONString(returnParams));
		}

		return returnParams;
	}

	private OrderDetailResponse remoteOrderDetail(Long orderId) {
		Preconditions.checkNotNull(orderId);
		ObjectDataResponse<OrderDetailResponse> dataResponse =  orderClient.orderDetail(orderId);
		if(dataResponse != null && dataResponse.getData() != null){
			return dataResponse.getData();
		}else{
			Shift.fatal(StatusCode.ORDER_NOT_EXISTS);
		}
		return null;
	}

	public void payNotify(HttpServletRequest request, HttpServletResponse response) {
		Long businessId = (Long) request.getSession().getAttribute("businessId");
		String xmlMsg = RequestUtil.readData(request);
		LOGGER.info("支付成功通知="+xmlMsg);
		Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);

		String result_code  = params.get("result_code");
		String orderId      = params.get("out_trade_no");// 商户订单号

		// 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
		// 避免已经成功、关闭、退款的订单被再次更新
		String result = "";
		SysBusinessWeixinConfig weixinConfig = remoteWeixinConfig(businessId);
		if(weixinConfig != null) {
			if (PaymentKit.verifyNotify(params, weixinConfig.getPayKey())) {
				if (("SUCCESS").equals(result_code)) {//更新订单信息
					LOGGER.info("微信支付成功更新订单信息,orderId={}", orderId);
					PaymentRequest paymentRequest = new PaymentRequest();
					paymentRequest.setOutTradeNo(orderId);
					orderClient.payConfirm(paymentRequest);
					Map<String, String> xml = new HashMap<String, String>();
					xml.put("return_code", "SUCCESS");
					xml.put("return_msg", "OK");
					result = PaymentKit.toXml(xml);
				}
			}
			PrintWriter writer = null;
			try {
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);

				response.setContentType("text/plain");
				response.setCharacterEncoding(Charsets.UTF_8.name());
				writer = response.getWriter();
				writer.write(result);
				writer.flush();
			} catch (IOException e) {
				LOGGER.error("pay_notify Exception", e);
			} finally {
				IOUtils.closeQuietly(writer);
			}
		}
	}

	private CrmUser remoteUserByOpenId(String openId){
		ObjectDataResponse<CrmUser> dataResponse = accountClient.queryRemoteUserByOpenId(openId);
		if(dataResponse != null && dataResponse.getData() != null){
			return  dataResponse.getData();
		}else {
			Shift.fatal(StatusCode.USER_NOT_EXISTS);
		}
		return null;
	}

	private SysBusinessWeixinConfig remoteWeixinConfig(Long businessId){
		Preconditions.checkNotNull(businessId);
		ObjectDataResponse<SysBusinessWeixinConfig> dataResponse =  productClient.weixinConfig(businessId);
		if(dataResponse != null && dataResponse.getData() != null){
			return dataResponse.getData();
		}else{
			Shift.fatal(StatusCode.WEIXIN_CONFIG_NULL);
		}
		return null;
	}

	/**
	 * 获取当前进入微信公众号用户的openid
	 * @param request
	 * @param response
	 * @return
	 */
	public String getUserOpenId(HttpServletRequest request, HttpServletResponse response){
		Object openIdObj = request.getSession().getAttribute("openId");
		Long businessId = (Long) request.getSession().getAttribute("businessId");
		String openId = "";
		if(openIdObj!=null){
			openId = openIdObj.toString();
		}
		if (StringUtils.isEmpty(openId)) {
			openId = (String) request.getParameter("openId");
			if(StringUtils.isEmpty(openId)){
				String code = (String) request.getParameter("code");
				if(StringUtils.isEmpty(code)){
//					String requestURILocall = getRequestUrl(request);
//					LOGGER.info("通过页面重定向获取code,页面当前请求 URL={}",requestURILocall);
//					String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
//					try {
//						requestUrl = requestUrl.replace("APPID",WeixinConstant.APPID).replace("REDIRECT_URI",URLEncoder.encode(requestURILocall,"UTF-8"));
//						LOGGER.info("通过页面重定向获取code,最终重定向 requestUrl={}",requestUrl);
//						response.sendRedirect(requestUrl);
//					} catch (IOException e) {
//						LOGGER.error("获取当前进入微信公众号用户的openid进行页面重定向异常",e);
//					}
					LOGGER.error("获取当前进入微信公众号用户的openid异常,code为null");
				} else {
					SysBusinessWeixinConfig weixinConfig = remoteWeixinConfig(businessId);
					if(weixinConfig != null){
						openId = getUserOpenId(weixinConfig.getAppId(), weixinConfig.getAppSecret(), code);
					}
				}
			}
			request.getSession().setAttribute("openId", openId);
		}
		LOGGER.info("当前进入微信公众号用户的 openId={}",openId);
		if(StringUtils.isEmpty(openId)){
			openId = "oTZdswPIB9w6XtcIOjZx6nAKy7SA";
			LOGGER.info("获取openId为null 直接使用自己的 openId={}",openId);
		}
		return openId;
	}

	/**
	 * 获取openId
	 * @param appid
	 * @param appSecret
	 * @param code
	 * @return
	 * 采用 网页授权获取 access_token API：SnsAccessTokenApi获取
	 */
	private String getUserOpenId(String appid, String appSecret, String code){
		LOGGER.info("根据code获取openId,code={}",code);
		String openId = "";
		try {
			String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
			requestUrl = requestUrl.replace("APPID", appid).replace("SECRET", appSecret).replace("CODE", code);
			String response = HttpRequestClient.invokeGet(requestUrl, null, null, null);
			if(checkWXResult(response)){
				Map<String, Object> resultMap = JSONObject.parseObject(response, new TypeReference<Map<String, Object>>() {});
				if (resultMap.get("openid") != null) {
					openId = resultMap.get("openid").toString();
				}
			}
		} catch (Exception e) {
			LOGGER.error("根据code获取openId异常", e);
		}
		return openId;
	}

	/*public String enchashment(Long businessId,WithdrawRequest request) {
		ConfigRequest configRequest = new ConfigRequest();
		configRequest.setConfigKey("enchashment_type");
		String enchashmentType = productClient.sysConfig(businessId,configRequest).getData();
		if(enchashmentType.equals("0")){
			LOGGER.info("企业付款到银行卡【人工】");
			return artificialBankCard(request,response);
		} else if(enchashmentType.equals("1")){
			LOGGER.info("企业付款到银行卡【自动】");
			return paybank(request,response);
		} else if(enchashmentType.equals("2")){
			LOGGER.info("发送普通红包(提现)");
			return sendredpack(request,response);
		} else if(enchashmentType.equals("3")){
			LOGGER.info("企业付款到零钱(提现)");
			return promotionTransfers(request,response);
		} else {
			LOGGER.info("企业付款到银行卡【人工】");
			return artificialBankCard(request,response);
		}
	}

	private String artificialBankCard(HttpServletRequest request,HttpServletResponse response) {

		Date now = new Date();//当前时间
		Long checkCashCredit = new BigDecimal(request.getParameter("checkCashCredit")).multiply(new BigDecimal(100)).longValue();//兑换积分 1积分就是1块钱   单位:分
		BizCustomer bizCustomer = bizCustomerService.queryBizCustomerByOpenId(WeixinUtil.getUserOpenId(request,response));

		if((StringUtils.isBlank(bizCustomer.getPayeeAccount()) || StringUtils.isBlank(bizCustomer.getPayeeUsername()) || bizCustomer.getPayeeOpenBank() == null) && StringUtils.isBlank(bizCustomer.getPayeeZfb())){
			return "请先绑定银行卡信息和支付宝账号信息";
		}

		String checkMsg =  encashmentCheck(bizCustomer,checkCashCredit,now);
		if(checkMsg.equals("success")){
			LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>开始调用人工银行卡付款>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			try {
				//按正常购买单对待
				String mchBillno = RedPackUtil.createBillNo();//微信订单号
				//提现记录表写入数据
				BizCashRecord bizCashRecord = new BizCashRecord();
				bizCashRecord.setConsumerId(bizCustomer.getConsumerId());
				bizCashRecord.setCreateTime(now);
				bizCashRecord.setCredit(checkCashCredit);
				bizCashRecord.setStatus(0);
				bizCashRecord.setMchBillno(mchBillno);
				bizCashRecord.setType(2);//0-微信红包  1-企业付款  2-人工付款到银行卡
				bizCashRecord.setMemo("人工付款到银行卡添加付款信息");
				bizCashRecordService.insert(bizCashRecord);

				//更新用户表用户可提现积分
				bizCustomerService.updateSendredpackCredit(bizCustomer.getConsumerId(), checkCashCredit);

				return "success";
			} catch (Exception e) {
				LOGGER.error("积分兑换提现调用人工银行卡付款异常",e);
				return "积分提现异常,请稍后再试";
			}
		} else {
			return  checkMsg;
		}
	}

	private String paybank(HttpServletRequest request,HttpServletResponse response) {

		Date now = new Date();//当前时间
		Long checkCashCredit = Long.valueOf(request.getParameter("checkCashCredit"))*100;//兑换积分 1积分就是1块钱   单位:分
		BizCustomer bizCustomer = bizCustomerService.queryBizCustomerByOpenId(WeixinUtil.getUserOpenId(request,response));

		if(StringUtils.isBlank(bizCustomer.getPayeeAccount()) || StringUtils.isBlank(bizCustomer.getPayeeUsername()) || bizCustomer.getPayeeOpenBank() == null){
			return "请先绑定银行卡信息";
		}

		String checkMsg =  encashmentCheck(bizCustomer,checkCashCredit,now);
		if(checkMsg.equals("success")){
			LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>开始调用企业付款到银行卡>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			try {

				//按正常购买单对待
				Integer total_amount = checkCashCredit.intValue();//微信付款金额 单位  分
				String mchBillno = RedPackUtil.createBillNo();//微信订单号

				//提现记录表写入数据
				BizCashRecord bizCashRecord = new BizCashRecord();
				bizCashRecord.setConsumerId(bizCustomer.getConsumerId());
				bizCashRecord.setCreateTime(now);
				bizCashRecord.setCredit(checkCashCredit);
				bizCashRecord.setMchBillno(mchBillno);
				bizCashRecord.setType(3);//类型  0-微信红包  1-企业付款到零钱  2-企业付款到银行卡(人工) 3-企业付款到银行卡(自动)

				//请求企业付款到银行卡
				PayBank payBank = new PayBank();
				payBank.setMch_id(WeixinConstant.MCH_ID);
				payBank.setPartner_trade_no(mchBillno);
				payBank.setNonce_str(WeixinUtil.createNonceStr());
				payBank.setEnc_bank_no(bizCustomer.getPayeeAccount().trim());
				payBank.setEnc_true_name(bizCustomer.getPayeeUsername().trim());
				payBank.setBank_code(String.valueOf(bizCustomer.getPayeeBankCode()));
				payBank.setAmount(total_amount);
				String sign = PaymentKit.createSign(CommonUtils.obj2Map(payBank), WeixinConstant.KEY);
				payBank.setSign(sign);
				LOGGER.info("调用企业付款到银行卡请求参数,payBank={}",JSONObject.toJSONString(payBank));
				Map<String, String> result = PaymentKit.xmlToMap(RedPackUtil.RedPackPost(WeixinRequestConstant.PAY_BANK, PaymentKit.objToXml(payBank)));
				LOGGER.info("请求企业付款到银行卡结果,result={}",JSONObject.toJSONString(result));
				String return_code = result.get("return_code");
				String result_code = result.get("result_code");
				if ("SUCCESS".equalsIgnoreCase(return_code) && "SUCCESS".equalsIgnoreCase(result_code)) {//成功
					bizCashRecord.setStatus(1);//1-成功
					bizCashRecordService.insert(bizCashRecord);
					//更新用户表用户可提现积分
					bizCustomerService.updateSendredpackCredit(bizCustomer.getConsumerId(), checkCashCredit);
					return "success";
				} else {//失败
					String return_msg = result.get("return_msg");
					bizCashRecord.setStatus(2);//提现失败
					bizCashRecord.setMemo(return_msg);
					bizCashRecordService.insert(bizCashRecord);
					return return_msg;
				}
			} catch (Exception e) {
				LOGGER.error("积分兑换提现调用企业付款到银行卡异常",e);
				return "积分提现异常,请稍后再试";
			}
		} else {
			return  checkMsg;
		}
	}

	private String sendredpack(HttpServletRequest request,HttpServletResponse response) {


		Date now = new Date();//当前时间
		Long checkCashCredit = new BigDecimal(request.getParameter("checkCashCredit")).multiply(new BigDecimal(100)).longValue();//兑换积分 1积分就是1块钱   单位:分
		BizCustomer bizCustomer = bizCustomerService.queryBizCustomerByOpenId(WeixinUtil.getUserOpenId(request,response));

		String checkMsg =  encashmentCheck(bizCustomer,checkCashCredit,now);
		if(checkMsg.equals("success")){
			LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>开始调用微信发送普通红包>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			try {
				//按正常购买单对待
				Integer total_amount = checkCashCredit.intValue();//微信付款金额 单位  分
				String mchBillno = RedPackUtil.createBillNo();//微信订单号

				//提现记录表写入数据
				BizCashRecord bizCashRecord = new BizCashRecord();
				bizCashRecord.setConsumerId(bizCustomer.getConsumerId());
				bizCashRecord.setCreateTime(now);
				bizCashRecord.setCredit(checkCashCredit);
				bizCashRecord.setMchBillno(mchBillno);
				bizCashRecord.setType(0);//0-微信红包  1-企业付款

				//请求微信发红包
				RedPack redPack = new RedPack();
				redPack.setAct_name("海峡两岸官网商城积分兑换");
				redPack.setClient_ip(IpKit.getRealIp(request));
				redPack.setMch_billno(mchBillno);
				redPack.setMch_id(WeixinConstant.MCH_ID);
				redPack.setNonce_str(WeixinUtil.createNonceStr());
				redPack.setRe_openid(bizCustomer.getWechatOpenid());
				redPack.setRemark("海峡两岸官网商城微信积分兑换");
				redPack.setSend_name(sysConfigService.getValuesByKey("app_name"));//微信账号名称
				redPack.setTotal_num(1);
				redPack.setTotal_amount(total_amount);//微信单位为分
				redPack.setWishing("感谢您的订购");
				redPack.setWxappid(WeixinConstant.APPID);
				redPack.setScene_id(sysConfigService.getValuesByKey("redpack_scene_id"));//微信红包场景ID
				String sign = PaymentKit.createSign(CommonUtils.obj2Map(redPack), WeixinConstant.KEY);
				redPack.setSign(sign);
				LOGGER.info("微信发红包请求参数,redPack={}",JSONObject.toJSONString(redPack));
				Map<String, String> result = PaymentKit.xmlToMap(RedPackUtil.RedPackPost(WeixinRequestConstant.SEND_REDPACK, MessageUtil.RedPackToXml(redPack)));
				LOGGER.info("请求微信发红包结果,result={}",JSONObject.toJSONString(result));
				String return_code = result.get("return_code");
				String result_code = result.get("result_code");
				if ("SUCCESS".equalsIgnoreCase(return_code) && "SUCCESS".equalsIgnoreCase(result_code)) {//成功

					//插入提现记录为成功
					bizCashRecord.setStatus(1);//1-成功
					bizCashRecordService.insert(bizCashRecord);

					//更新用户表用户可提现积分
					bizCustomerService.updateSendredpackCredit(bizCustomer.getConsumerId(), checkCashCredit);

					return "success";
				} else {//失败
					String return_msg = result.get("return_msg");
					bizCashRecord.setStatus(2);//提现失败
					bizCashRecord.setMemo(return_msg);
					bizCashRecordService.insert(bizCashRecord);
					return return_msg;
				}
			} catch (Exception e) {
				LOGGER.error("积分兑换提现调用微信发送普通红包异常",e);
				return "积分提现异常,请稍后再试";
			}
		} else {
			return  checkMsg;
		}
	}

	private String promotionTransfers(HttpServletRequest request,HttpServletResponse response) {

		Date now = new Date();//当前时间
		Long checkCashCredit = new BigDecimal(request.getParameter("checkCashCredit")).multiply(new BigDecimal(100)).longValue();//兑换积分 1积分就是1块钱   单位:分
		BizCustomer bizCustomer = bizCustomerService.queryBizCustomerByOpenId(WeixinUtil.getUserOpenId(request,response));

		String checkMsg =  encashmentCheck(bizCustomer,checkCashCredit,now);
		if(checkMsg.equals("success")){
			LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>开始调用企业付款到零钱>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			try {
				//按正常购买单对待
				Integer total_amount = checkCashCredit.intValue();//微信付款金额 单位  分
				String mchBillno = RedPackUtil.createBillNo();//微信订单号

				//提现记录表写入数据
				BizCashRecord bizCashRecord = new BizCashRecord();
				bizCashRecord.setConsumerId(bizCustomer.getConsumerId());
				bizCashRecord.setCreateTime(now);
				bizCashRecord.setCredit(checkCashCredit);
				bizCashRecord.setMchBillno(mchBillno);
				bizCashRecord.setType(1);//0-微信红包  1-企业付款

				//请求企业付款
				Transfers transfers = new Transfers();
				transfers.setMch_appid(WeixinConstant.APPID);
				transfers.setMchid(WeixinConstant.MCH_ID);
				transfers.setNonce_str(WeixinUtil.createNonceStr());
				transfers.setPartner_trade_no(mchBillno);
				transfers.setOpenid(bizCustomer.getWechatOpenid());
				transfers.setCheck_name("NO_CHECK");
				transfers.setAmount(total_amount);
				transfers.setDesc("海峡两岸官网商城微信积分兑换");
				transfers.setSpbill_create_ip(NetUtils.getIpAddress(request));
				LOGGER.info("微信请求企业付款参数,transfers={}",JSONObject.toJSONString(transfers));
				transfers.setSign(PaymentKit.createSign(CommonUtils.obj2Map(transfers), WeixinConstant.KEY));
				Map<String, String> result = PaymentKit.xmlToMap(RedPackUtil.RedPackPost(WeixinRequestConstant.PROMOTION_TRANSFERS, PaymentKit.objToXml(transfers)));
				LOGGER.info("请求企业付款结果,result={}",JSONObject.toJSONString(result));
				String return_code = result.get("return_code");
				String result_code = result.get("result_code");
				if ("SUCCESS".equalsIgnoreCase(return_code) && "SUCCESS".equalsIgnoreCase(result_code)) {//成功
					bizCashRecord.setStatus(1);//1-成功
					bizCashRecordService.insert(bizCashRecord);
					//更新用户表用户可提现积分
					bizCustomerService.updateSendredpackCredit(bizCustomer.getConsumerId(), checkCashCredit);
					return "success";
				} else {//失败
					String return_msg = result.get("return_msg");
					bizCashRecord.setStatus(2);//提现失败
					bizCashRecord.setMemo(return_msg);
					bizCashRecordService.insert(bizCashRecord);
					return return_msg;
				}
			} catch (Exception e) {
				LOGGER.error("积分兑换提现调用企业付款到零钱异常",e);
				return "积分提现异常,请稍后再试";
			}
		} else {
			return  checkMsg;
		}
	}*/
}
