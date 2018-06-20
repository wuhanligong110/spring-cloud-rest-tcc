package com.miget.hxb.controller.client;

import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.domain.BizOrder;
import com.miget.hxb.model.request.PaymentRequest;
import com.miget.hxb.model.request.PlaceOrderRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.model.response.OrderDetailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author hxb
 */
@Component
public class OrderClientFallback implements OrderClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderClientFallback.class);

    @Override
    public ObjectDataResponse<BizOrder> placeOrder(PlaceOrderRequest request) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public ObjectDataResponse payConfirm(PaymentRequest request) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public ObjectDataResponse<OrderDetailResponse> orderDetail(Long orderId) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    private void didNotGetResponse() {
        LOGGER.error("service '{}' has become unreachable", SERVICE_ID);
    }

}
