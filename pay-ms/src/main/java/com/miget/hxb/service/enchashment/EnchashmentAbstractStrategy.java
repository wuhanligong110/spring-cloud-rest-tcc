package com.miget.hxb.service.enchashment;

import com.google.common.base.Preconditions;
import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.controller.client.AccountClient;
import com.miget.hxb.controller.client.ProductClient;
import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.model.request.ConfigRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

public abstract class EnchashmentAbstractStrategy implements EnchashmentStrategy {

    @Resource
    private AccountClient accountClient;
    @Resource
    private ProductClient productClient;

    @Override
    public boolean encashmentCheck(Long businessId, Long userId, Integer userAccount, BigDecimal amount) {
        //工作日12:00 ~ 17:00才可以提现
        Date now = new Date();
        int weekOfDate = DateUtils.getWeekOfDate(now);//当前星期
        if(weekOfDate == 1 || weekOfDate == 7){//双休日不可提现
            Shift.fatal(StatusCode.WITHDRAW_TIME_ERRO);
        } else {
            String start = StringUtils.join(new Object[]{DateUtils.format(now, DateUtils.FORMAT_SHORT)," 08:00:00"});
            String end = StringUtils.join(new Object[]{DateUtils.format(now, DateUtils.FORMAT_SHORT)," 12:00:00"});
            if(now.before(DateUtils.parse(start)) || now.after(DateUtils.parse(end))){
                Shift.fatal(StatusCode.WITHDRAW_TIME_ERRO);
            }
        }
        ConfigRequest configRequest = new ConfigRequest();
        configRequest.setConfigKey("cash_credit_swicth");

        //提现开关是否开启
        if(remoteSwitch(businessId,configRequest)){
            if(amount.compareTo(new BigDecimal("0")) < 0){
                Shift.fatal(StatusCode.WITHDRAW_PARAMS_ERRO);
            }
            CrmUser user = remoteFindUser(userId);
            if(amount.compareTo(new BigDecimal(user.getAccWithdrawalCredit() / 100)) < 0){
                Shift.fatal(StatusCode.WITHDRAW_PARAMS_ERRO);
            }
        } else {
            Shift.fatal(StatusCode.WITHDRAW_PARAMS_ERRO);
        }
        return true;
    }

    private boolean remoteSwitch(Long businessId, ConfigRequest configRequest){
        ObjectDataResponse<String> configValue = productClient.sysConfig(businessId,configRequest);
        Preconditions.checkNotNull(configValue);
        if(configValue.getData().equals("on")){
            return true;
        }else {
            return false;
        }
    }

    private CrmUser remoteFindUser(Long userId){
        ObjectDataResponse<CrmUser> userData = accountClient.findUser(userId);
        Preconditions.checkNotNull(userData);
        CrmUser user = new CrmUser();
        if(userData.getData() == null){
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }else {
            user = userData.getData();
        }
        return user;
    }
}
