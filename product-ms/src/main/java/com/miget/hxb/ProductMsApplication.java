package com.miget.hxb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
/*@EnableScheduling*/
@EnableFeignClients
@MapperScan(basePackages = "com.miget.hxb.persistence", annotationClass = MyBatisRepository.class)
@SpringCloudApplication
public class ProductMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductMsApplication.class, args);
    }
}
