package com.miget.hxb.event.listener;

import com.google.common.base.Preconditions;
import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.event.RegisterAddCreditEvent;
import com.miget.hxb.model.request.CreditAddRequest;
import com.miget.hxb.service.BizCreditDetailService;
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

    private final BizCreditDetailService creditDetailService;

    @Autowired
    public RegisterAddCreditEventListener(BizCreditDetailService creditDetailService) {
        this.creditDetailService = creditDetailService;
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void onApplicationEvent(RegisterAddCreditEvent event) {
        Preconditions.checkNotNull(event);
        final CrmUser user = (CrmUser) event.getSource();
        Preconditions.checkNotNull(user);
        CreditAddRequest request = new CreditAddRequest();
        request.setUserId(user.getUserId());
        request.setCredit(20000l);
        request.setTypeValue(3);
        request.setRemark("注册送20元购物积分");
        creditDetailService.creditAdd(request);
    }

}
