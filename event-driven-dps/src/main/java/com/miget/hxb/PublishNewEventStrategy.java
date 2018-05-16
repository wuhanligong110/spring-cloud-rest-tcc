package com.miget.hxb;

import com.miget.hxb.domain.EventPublisher;
import com.miget.hxb.domain.type.EventStatus;
import com.miget.hxb.persistence.EventPublisherMapper;
import com.miget.hxb.domain.EventPublisher;
import com.miget.hxb.domain.type.EventStatus;
import com.miget.hxb.persistence.EventPublisherMapper;

import java.util.Set;

/**
 * @author Zhao Junjian
 */
public enum PublishNewEventStrategy implements BatchFetchEventStrategy {
    SINGLETON;

    @Override
    public Set<EventPublisher> execute(EventPublisherMapper mapper) {
        return mapper.selectLimitedEntityByEventStatus(EventStatus.NEW, 300);
    }
}
