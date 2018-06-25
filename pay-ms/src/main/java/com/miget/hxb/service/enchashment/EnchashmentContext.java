package com.miget.hxb.service.enchashment;

import java.math.BigDecimal;

public class EnchashmentContext {

    private EnchashmentStrategy enchashmentStrategy;

    public EnchashmentContext(EnchashmentStrategy enchashmentStrategy){
        this.enchashmentStrategy = enchashmentStrategy;
    }

    public void enchashment(Long businessId, Long userId, Integer userAccount, BigDecimal amount){
        boolean encashmentCheckResult = enchashmentStrategy.encashmentCheck(businessId,userId, userAccount, amount);
        boolean userAccountCheckResult = false;
        if(encashmentCheckResult){
            userAccountCheckResult = enchashmentStrategy.userAccountCheck(userId,userAccount);
        }
        if(userAccountCheckResult){
            String mchBillno = enchashmentStrategy.preWithdraw(businessId,userId, userAccount, amount);
            enchashmentStrategy.pay(businessId,userId,userAccount,amount,mchBillno);
        }
    }

}
