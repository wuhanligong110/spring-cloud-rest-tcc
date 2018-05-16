package com.miget.hxb.event;

import com.miget.hxb.domain.CrmUser;
import org.springframework.context.ApplicationEvent;

/**
 * @author hxb
 */
public class RegisterAddCreditEvent extends ApplicationEvent {
    private static final long serialVersionUID = -3561050469176976072L;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public RegisterAddCreditEvent(CrmUser user) {
        super(user);
    }
}
