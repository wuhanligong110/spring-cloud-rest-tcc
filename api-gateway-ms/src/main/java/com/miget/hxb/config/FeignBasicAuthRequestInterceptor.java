package com.miget.hxb.config;

import com.miget.hxb.context.BaseContextHandler;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {
    public FeignBasicAuthRequestInterceptor() {

    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", BaseContextHandler.getToken());
    }

}
