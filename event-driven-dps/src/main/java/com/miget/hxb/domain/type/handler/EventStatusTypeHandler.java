package com.miget.hxb.domain.type.handler;

import com.miget.hxb.domain.type.EventStatus;

/**
 * @author Zhao Junjian
 */
public class EventStatusTypeHandler extends GenericTypeHandler<EventStatus> {
    @Override
    public int getEnumIntegerValue(EventStatus parameter) {
        return parameter.getStatus();
    }
}
