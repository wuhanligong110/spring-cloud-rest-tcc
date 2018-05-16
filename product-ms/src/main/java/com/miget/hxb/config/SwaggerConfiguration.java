package com.miget.hxb.config;

import com.miget.hxb.model.swagger.SwaggerApiInfo;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.controller.StatusCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Zhao Junjian
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration extends SwaggerTemplate {

    @Bean
    public SwaggerApiInfo info() {
        return SwaggerApiInfo.builder().title("Product MicroService").version("v1").serviceUrl(null).statusList(extractStatusCodes()).build();
    }

    private List<ResponseMessage> extractStatusCodes() {
        final LinkedList<ResponseMessage> list = new LinkedList<>();
        for (StatusCode statusCodes : StatusCode.values()) {
            final ResponseMessageBuilder builder = new ResponseMessageBuilder();
            final ResponseMessage message = builder
                    .code(statusCodes.code())
                    .message(statusCodes.message())
                    .build();
            list.add(message);
        }
        return list;
    }

}
