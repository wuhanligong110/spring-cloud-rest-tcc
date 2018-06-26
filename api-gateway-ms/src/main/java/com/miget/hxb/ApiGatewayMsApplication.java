package com.miget.hxb;

import com.miget.hxb.filter.LoginFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableZuulProxy
@SpringCloudApplication
public class ApiGatewayMsApplication {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayMsApplication.class, args);
    }
}
