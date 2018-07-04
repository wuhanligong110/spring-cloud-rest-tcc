package com.miget.hxb.service.enchashment;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.controller.client.AccountClient;
import com.miget.hxb.controller.client.ProductClient;
import com.miget.hxb.domain.CrmUserAccountBind;
import com.miget.hxb.domain.SysBusinessWeixinConfig;
import com.miget.hxb.helper.WeixinHelper;
import com.miget.hxb.model.request.ConfigRequest;
import com.miget.hxb.model.request.CreditStatusRequest;
import com.miget.hxb.model.request.CreditSubRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.util.CommonUtils;
import com.miget.hxb.wx.constant.WeixinRequestConstant;
import com.miget.hxb.wx.model.RedPack;
import com.miget.hxb.wx.utils.MessageUtil;
import com.miget.hxb.wx.utils.PaymentKit;
import com.miget.hxb.wx.utils.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

public class EnchashmentSendRedpackStrategy extends EnchashmentAbstractStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnchashmentSendRedpackStrategy.class);

    @Resource
    private AccountClient accountClient;
    @Resource
    private ProductClient productClient;
    @Resource
    private WeixinHelper weixinHelper;

    /**
     * 检查用户绑定的账号是否满足此种提现方式
     *
     * @param userId
     * @param userAccount
     * @return
     */
    @Override
    public boolean userAccountCheck(Long userId, Integer userAccount) {
        CrmUserAccountBind accountBind = remoteUserAccount(userId,userAccount);
        if(accountBind.getAccountType() == 1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 生成提现记录，扣减余额
     */
    @Override
    public String preWithdraw(Long businessId,Long userId, Integer userAccount, BigDecimal amount) {
        String mchBillno = weixinHelper.createBillNo(businessId);
        CreditSubRequest subRequest = new CreditSubRequest();
        subRequest.setCredit(amount.multiply(new BigDecimal("100")).toBigInteger().longValue());
        subRequest.setOrderId(mchBillno);
        subRequest.setRemark("提现");
        subRequest.setTypeValue(3);
        subRequest.setUserId(Math.toIntExact(userId));
        accountClient.creditSub(subRequest);
        return mchBillno;
    }

    /**
     * 系统支付用户提现
     */
    @Override
    public void pay(Long businessId,Long userId, Integer userAccount, BigDecimal amount,String mchBillno) {
        CrmUserAccountBind accountBind = remoteUserAccount(userId,userAccount);
        SysBusinessWeixinConfig weixinConfig = remoteWeixinConfig(businessId);

        try {
            ConfigRequest configRequest = new ConfigRequest();
            configRequest.setConfigKey("app_name");
            String appName = productClient.sysConfig(businessId,configRequest).getData();
            configRequest.setConfigKey("redpack_scene_id");
            String redpackSceneId = productClient.sysConfig(businessId,configRequest).getData();

            RedPack redPack = new RedPack();
            redPack.setAct_name("商城积分兑换");
            //TODO 获取真实IP
            redPack.setClient_ip("127.0.0.1");
            redPack.setMch_billno(mchBillno);
            redPack.setMch_id(weixinConfig.getMchId());
            redPack.setNonce_str(WeixinUtil.createNonceStr());
            redPack.setRe_openid(accountBind.getAccountCard());
            redPack.setRemark("商城微信积分兑换");
            redPack.setSend_name(appName);//微信账号名称
            redPack.setTotal_num(1);
            redPack.setTotal_amount(amount.multiply(new BigDecimal("100")).toBigInteger().intValue());//微信单位为分
            redPack.setWishing("感谢您的订购");
            redPack.setWxappid(weixinConfig.getAppId());
            redPack.setScene_id(redpackSceneId);//微信红包场景ID
            String sign = PaymentKit.createSign(CommonUtils.obj2Map(redPack), weixinConfig.getPayKey());
            redPack.setSign(sign);
            LOGGER.info("微信发红包请求参数,redPack={}",JSONObject.toJSONString(redPack));
            Map<String, String> result = PaymentKit.xmlToMap(weixinHelper.redPackPost(businessId,WeixinRequestConstant.SEND_REDPACK, MessageUtil.RedPackToXml(redPack)));
            LOGGER.info("请求微信发红包结果,result={}",JSONObject.toJSONString(result));
            String return_code = result.get("return_code");
            String result_code = result.get("result_code");
            if ("SUCCESS".equalsIgnoreCase(return_code) && "SUCCESS".equalsIgnoreCase(result_code)) {//成功
                //更新用户提现状态--成功
                CreditStatusRequest creditStatusRequest = new CreditStatusRequest();
                creditStatusRequest.setOrderId(mchBillno);
                creditStatusRequest.setStatus(1);
                creditStatusRequest.setUserId(Math.toIntExact(userId));
                accountClient.creditStatus(creditStatusRequest);
            } else {//失败
                //更新用户提现状态--失败
                CreditStatusRequest creditStatusRequest = new CreditStatusRequest();
                creditStatusRequest.setOrderId(mchBillno);
                creditStatusRequest.setStatus(2);
                creditStatusRequest.setUserId(Math.toIntExact(userId));
                accountClient.creditStatus(creditStatusRequest);
            }
        } catch (Exception e) {
            LOGGER.error("提现调用微信发送普通红包异常",e);
           Shift.fatal(StatusCode.WITHDRAW_PARAMS_ERRO);
        }
    }

    private CrmUserAccountBind remoteUserAccount(Long userId, Integer userAccount){
        ObjectDataResponse<CrmUserAccountBind> bindData = accountClient.userAccountInfo(userId,userAccount);
        Preconditions.checkNotNull(bindData);
        CrmUserAccountBind accountBind = new CrmUserAccountBind();
        if(bindData.getData() != null){
            accountBind = bindData.getData();
        }else{
            Shift.fatal(StatusCode.USER_ACCOUNT_NOT_EXISTS);
        }
        return accountBind;
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
}
