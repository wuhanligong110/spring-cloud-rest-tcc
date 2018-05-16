package com.miget.hxb.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.miget.hxb.provider.ApplicationContextProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.jboss.logging.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoDBAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private static final Logger logger = Logger.getLogger(MongoDBAppender.class);

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        MongoTemplate mongoTemplate = ApplicationContextProvider.getBean(MongoTemplate.class);
        if (mongoTemplate != null) {
            final BasicDBObject doc = (BasicDBObject) JSON.parse(iLoggingEvent.getMessage());
            mongoTemplate.insert(doc, "logs_request");
        }
    }
}
