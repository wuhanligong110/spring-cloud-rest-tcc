package com.miget.hxb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
@SpringCloudApplication
@MapperScan(basePackages = "com.miget.hxb.persistence", annotationClass = MyBatisRepository.class)
public class ActivityMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivityMsApplication.class, args);
    }
}
