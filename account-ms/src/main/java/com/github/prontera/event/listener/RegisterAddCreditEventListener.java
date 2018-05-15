package com.github.prontera.event.listener;

import com.github.prontera.domain.CrmUser;
import com.github.prontera.event.RegisterAddCreditEvent;
import com.github.prontera.service.CrmUserService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zhao Junjian
 */
@Component
public class RegisterAddCreditEventListener implements ApplicationListener<RegisterAddCreditEvent> {

    private final CrmUserService userService;

    @Autowired
    public RegisterAddCreditEventListener(CrmUserService userService) {
        this.userService = userService;
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void onApplicationEvent(RegisterAddCreditEvent event) {
        Preconditions.checkNotNull(event);
        final CrmUser user = (CrmUser) event.getSource();
        CrmUser crmUser = new CrmUser();
        Preconditions.checkNotNull(user);
        crmUser.setUserId(user.getUserId());
        if(user.getAccWithdrawalCredit() == null){
            crmUser.setAccWithdrawalCredit(20000l);
        }else{
            crmUser.setAccWithdrawalCredit(user.getAccWithdrawalCredit()+20000);
        }
        userService.updateNonNullProperties(crmUser);
    }

}
