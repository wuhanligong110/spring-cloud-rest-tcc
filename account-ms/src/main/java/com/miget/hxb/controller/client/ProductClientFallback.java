package com.miget.hxb.controller.client;

import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.domain.SysBusinessWeixinConfig;
import com.miget.hxb.model.response.ObjectDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author hxb
 */
@Component
public class ProductClientFallback implements ProductClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductClientFallback.class);

    @Override
    public ObjectDataResponse<SysBusinessWeixinConfig> weixinConfig(Long businessId) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    private void didNotGetResponse() {
        LOGGER.error("service '{}' has become unreachable", SERVICE_ID);
    }

}
