package com.miget.hxb.annotation;

import java.lang.annotation.*;

/**
 * 注解类，用来无侵入的实现分布式事务
 * */

@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.METHOD)  
@Documented  
public @interface MqSender {  
	String exchange() default "";   //要发送的交换机
	String routingkey() default "";    //要发送的key
}  
