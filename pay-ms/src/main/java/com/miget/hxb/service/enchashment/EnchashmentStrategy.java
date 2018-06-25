package com.miget.hxb.service.enchashment;

import java.math.BigDecimal;

public interface EnchashmentStrategy {

    /**
     * 提现参数检查
     * @return
     */
    boolean encashmentCheck(Long businessId, Long userId, Integer userAccount, BigDecimal amount);

    /**
     * 检查用户绑定的账号是否满足此种提现方式
     * @param userId
     * @param userAccount
     * @return
     */
    boolean userAccountCheck(Long userId,Integer userAccount);

    /**
     * 生成提现记录，扣减余额
     */
    String preWithdraw(Long businessId,Long userId, Integer userAccount, BigDecimal amount);

    /**
     * 系统支付用户提现
     */
    void pay(Long businessId,Long userId, Integer userAccount, BigDecimal amount,String mchBillno);
}
