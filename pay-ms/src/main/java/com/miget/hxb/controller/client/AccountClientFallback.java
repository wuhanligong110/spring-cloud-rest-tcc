package com.miget.hxb.controller.client;

import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.domain.CrmUserAccountBind;
import com.miget.hxb.model.request.CreditStatusRequest;
import com.miget.hxb.model.request.CreditSubRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

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
    public ObjectDataResponse<CrmUser> queryRemoteUserByOpenId(@PathVariable("openid")String openid) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public ObjectDataResponse<CrmUserAccountBind> userAccountInfo(Long userId, Integer userAccount) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public ObjectDataResponse creditSub(CreditSubRequest request) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public ObjectDataResponse creditStatus(CreditStatusRequest request) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    private void didNotGetResponse() {
        LOGGER.error("service '{}' has become unreachable", SERVICE_ID);
    }

}
