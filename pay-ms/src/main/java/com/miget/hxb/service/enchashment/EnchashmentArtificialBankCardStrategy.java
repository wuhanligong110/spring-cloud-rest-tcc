package com.miget.hxb.service.enchashment;

import com.google.common.base.Preconditions;
import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.controller.client.AccountClient;
import com.miget.hxb.controller.client.ProductClient;
import com.miget.hxb.domain.CrmUserAccountBind;
import com.miget.hxb.helper.WeixinHelper;
import com.miget.hxb.model.request.CreditSubRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;

public class EnchashmentArtificialBankCardStrategy extends EnchashmentAbstractStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnchashmentArtificialBankCardStrategy.class);

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
        if(accountBind.getAccountType() == 3){
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

}
