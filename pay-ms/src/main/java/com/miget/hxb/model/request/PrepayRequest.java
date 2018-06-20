package com.miget.hxb.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class PrepayRequest{

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 收件地址
     */
    private Integer addressId;
    /**
     * 商家ID
     */
    private Long businessId;
    /**
     * 订单明细
     */
    private List<PlaceOrderItemRequest> orderItems;
}
