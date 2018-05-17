package com.miget.hxb.controller.client;

import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.model.CrmUser;
import com.miget.hxb.model.response.ObjectDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Zhao Junjian
 */
@Component
public class AccountClientFallback implements AccountClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountClientFallback.class);

    @Override
    public ObjectDataResponse<CrmUser> findUser(@PathVariable("userId") Long userId) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    private void didNotGetResponse() {
        LOGGER.error("service '{}' has become unreachable", SERVICE_ID);
    }

}
