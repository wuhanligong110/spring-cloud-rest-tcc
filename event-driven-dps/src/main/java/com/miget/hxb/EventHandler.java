package com.miget.hxb;

import com.miget.hxb.domain.EventSubscriber;
import com.miget.hxb.persistence.EventSubscriberMapper;
import com.miget.hxb.domain.EventSubscriber;
import com.miget.hxb.persistence.EventSubscriberMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Zhao Junjian
 */
public abstract class EventHandler {
    @Autowired
    private EventSubscriberMapper mapper;

    public EventSubscriberMapper getMapper() {
        return mapper;
    }

    public void setMapper(EventSubscriberMapper mapper) {
        this.mapper = mapper;
    }

    public abstract void handle(EventSubscriber subscriber);
}
