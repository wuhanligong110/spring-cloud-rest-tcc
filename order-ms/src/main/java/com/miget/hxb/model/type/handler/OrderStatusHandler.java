package com.miget.hxb.model.type.handler;

import com.miget.hxb.domain.type.handler.GenericTypeHandler;
import com.miget.hxb.model.type.OrderStatus;

/**
 * @author Zhao Junjian
 */
public class OrderStatusHandler extends GenericTypeHandler<OrderStatus> {

    @Override
    public int getEnumIntegerValue(OrderStatus parameter) {
        return parameter.getCode();
    }
}
