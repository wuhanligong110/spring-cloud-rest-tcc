package com.miget.hxb.controller.client;

import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.domain.CrmUserAddress;
import com.miget.hxb.model.response.ObjectDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author hxb
 */
@Component
public class AccountClientFallback implements AccountClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountClientFallback.class);

    @Override
    public ObjectDataResponse<CrmUser> findUser(Long userId) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public ObjectDataResponse<CrmUserAddress> userAddressDetail(Long addressId) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    private void didNotGetResponse() {
        LOGGER.error("service '{}' has become unreachable", SERVICE_ID);
    }

}
