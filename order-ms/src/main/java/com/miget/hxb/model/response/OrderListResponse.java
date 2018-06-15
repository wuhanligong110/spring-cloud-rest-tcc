package com.miget.hxb.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class OrderListResponse implements Response {
    private static final long serialVersionUID = -6113615255825866969L;

    @JsonProperty("orderId")
    @ApiModelProperty(value = "订单ID", example = "111", required = true)
    private Long orderId;

    @JsonProperty("outTradeNo")
    @ApiModelProperty(value = "订单号", example = "91fa53e0010c404c8bea8be379f36e00", required = true)
    private String outTradeNo;

    @JsonProperty("amount")
    @ApiModelProperty(value = "订单金额", example = "10000", required = true)
    private Long amount;

    @JsonProperty("orderStatus")
    @ApiModelProperty(value = "订单状态", example = "2", required = true)
    private Integer orderStatus;

    @JsonProperty("progress")
    @ApiModelProperty(value = "订单进度", example = "代付款", required = true)
    private String progress;

    @JsonProperty("orderItems")
    @ApiModelProperty(value = "订单明细", example = "", required = true)
    private List<OrderItemResponse> orderItems;

}
