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
import com.miget.hxb.model.request.CreditStatusRequest;
import com.miget.hxb.model.request.CreditSubRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.util.CommonUtils;
import com.miget.hxb.wx.constant.WeixinRequestConstant;
import com.miget.hxb.wx.model.Transfers;
import com.miget.hxb.wx.utils.PaymentKit;
import com.miget.hxb.wx.utils.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

public class EnchashmentPromotionTransfersStrategy extends EnchashmentAbstractStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnchashmentPromotionTransfersStrategy.class);

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
            Transfers transfers = new Transfers();
            transfers.setMch_appid(weixinConfig.getAppId());
            transfers.setMchid(weixinConfig.getMchId());
            transfers.setNonce_str(WeixinUtil.createNonceStr());
            transfers.setPartner_trade_no(mchBillno);
            transfers.setOpenid(accountBind.getAccountCard());
            transfers.setCheck_name("NO_CHECK");
            transfers.setAmount(amount.multiply(new BigDecimal("100")).toBigInteger().intValue());
            transfers.setDesc("商城微信积分兑换");
            //TODO 获取真实IP
            transfers.setSpbill_create_ip("127.0.0.1");
            LOGGER.info("微信请求企业付款参数,transfers={}",JSONObject.toJSONString(transfers));
            transfers.setSign(PaymentKit.createSign(CommonUtils.obj2Map(transfers), weixinConfig.getPayKey()));
            Map<String, String> result = PaymentKit.xmlToMap(weixinHelper.redPackPost(businessId,WeixinRequestConstant.PROMOTION_TRANSFERS, PaymentKit.objToXml(transfers)));
            LOGGER.info("请求企业付款结果,result={}",JSONObject.toJSONString(result));
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
            LOGGER.error("提现调用企业付款到银行卡异常",e);
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
