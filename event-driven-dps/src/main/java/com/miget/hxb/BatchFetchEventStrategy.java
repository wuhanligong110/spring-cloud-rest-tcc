package com.miget.hxb;

import com.miget.hxb.domain.EventPublisher;
import com.miget.hxb.persistence.EventPublisherMapper;
import com.miget.hxb.domain.EventPublisher;
import com.miget.hxb.persistence.EventPublisherMapper;

import java.util.Set;

/**
 * @author Zhao Junjian
 */
public interface BatchFetchEventStrategy {
    Set<EventPublisher> execute(EventPublisherMapper mapper);
}
