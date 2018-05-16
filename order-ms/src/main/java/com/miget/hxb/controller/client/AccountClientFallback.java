package com.miget.hxb.controller.client;

import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.model.User;
import com.miget.hxb.model.request.BalanceReservationRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.model.response.ReservationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Zhao Junjian
 */
@Component
public class AccountClientFallback implements AccountClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountClientFallback.class);

    @Override
    public ObjectDataResponse<User> findUser(@PathVariable("userId") Long userId) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public ReservationResponse reserve(@RequestBody BalanceReservationRequest request) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    private void didNotGetResponse() {
        LOGGER.error("service '{}' has become unreachable", SERVICE_ID);
    }

}
