package com.github.prontera.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.github.prontera.provider.ApplicationContextProvider;
import com.mongodb.BasicDBObject;
import org.jboss.logging.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoDBAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private static final Logger logger = Logger.getLogger(MongoDBAppender.class);

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        MongoTemplate mongoTemplate = ApplicationContextProvider.getBean(MongoTemplate.class);
        if (mongoTemplate != null) {
            final BasicDBObject doc = new BasicDBObject();
            doc.append("level", iLoggingEvent.getLevel().toString());
            doc.append("logger", iLoggingEvent.getLoggerName());
            doc.append("thread", iLoggingEvent.getThreadName());
            doc.append("message", iLoggingEvent.getFormattedMessage());
            mongoTemplate.insert(doc, "logs_request");
        }
    }
}
