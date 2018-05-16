package com.miget.hxb.config;

import com.miget.hxb.aspect.HibernateValidatorAspect;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.controller.StatusCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zhao Junjian
 */
@Configuration
public class AopConfiguration {

    @Bean
    public HibernateValidatorAspect hibernateValidatorAspect() {
        final int order = Byte.MAX_VALUE + 2;
        return new HibernateValidatorAspect(order, StatusCode.INVALID_MODEL_FIELDS);
    }

}
