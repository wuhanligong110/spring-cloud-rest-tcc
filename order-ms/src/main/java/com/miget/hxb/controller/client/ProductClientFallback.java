package com.miget.hxb.controller.client;

import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.model.CimProduct;
import com.miget.hxb.model.request.OrderCancelRequest;
import com.miget.hxb.model.request.StockReservationRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.model.response.ReservationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author hxb
 */
@Component
public class ProductClientFallback implements ProductClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductClientFallback.class);

    @Override
    public ReservationResponse reserve(@RequestBody StockReservationRequest request) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public ObjectDataResponse<Map<Integer, CimProduct>> findProducts(List<Integer> productIds) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public ObjectDataResponse<Integer> orderCancel(List<OrderCancelRequest> orderCancelRequests) {
        didNotGetResponse();
        Shift.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    private void didNotGetResponse() {
        LOGGER.error("service '{}' has become unreachable", SERVICE_ID);
    }
}
