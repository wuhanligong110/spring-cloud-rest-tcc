package com.miget.hxb.service.enchashment;

public class EnchashmentStrategyFactory {

    public static EnchashmentStrategy createEnchashmentStrategy(Integer enchashmentType){
        if (enchashmentType == 1) {//微信付款到银行卡
            return new EnchashmentPayBankStrategy();
        }else{
            return new EnchashmentPayBankStrategy();
        }
    }
}
