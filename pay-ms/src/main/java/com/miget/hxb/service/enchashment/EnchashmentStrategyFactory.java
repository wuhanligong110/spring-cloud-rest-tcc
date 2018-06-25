package com.miget.hxb.service.enchashment;

public class EnchashmentStrategyFactory {

    public static EnchashmentStrategy createEnchashmentStrategy(Integer enchashmentType){
        if (enchashmentType == 1) {
            return new EnchashmentPayBankStrategy();//微信付款到银行卡
        }else if(enchashmentType == 2){
            return new EnchashmentSendRedpackStrategy();//微信付款发送普通红包
        }else if(enchashmentType == 3){
            return new EnchashmentPromotionTransfersStrategy();//微信付款到零钱
        }else if(enchashmentType == 4){
            return new EnchashmentArtificialBankCardStrategy();//人工付款
        }else{
            return new  EnchashmentArtificialBankCardStrategy();
        }
    }
}
