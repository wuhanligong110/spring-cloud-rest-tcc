package com.miget.hxb.aspect;

import com.miget.hxb.config.ManualExceptionProperties;
import com.miget.hxb.exception.ManualException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Zhao Junjian
 */
@Aspect
public class ManualExceptionAspect implements Ordered {
    private final int order;
    private final ManualExceptionProperties properties;
    private static final Random RANDOM = new SecureRandom();

    public ManualExceptionAspect(int order, ManualExceptionProperties properties) {
        this.order = order;
        this.properties = properties;
    }

    @Around("@annotation(com.miget.hxb.RandomlyThrowsException)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        if (properties.isEnabled()) {
            if (RANDOM.nextInt(100) % properties.getFactor() == 0) {
                throw new ManualException("manual exception");
            }
        }
        return joinPoint.proceed();
    }

    @Override
    public int getOrder() {
        return order;
    }
}
