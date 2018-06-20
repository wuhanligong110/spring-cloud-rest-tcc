package com.miget.hxb.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class OrderItemResponse implements Response {
    private static final long serialVersionUID = -6113615255825866969L;

    @JsonProperty("productId")
    @ApiModelProperty(value = "产品ID", example = "2", required = true)
    private Integer productId;

    @JsonProperty("businessId")
    @ApiModelProperty(value = "商家ID", example = "1", required = true)
    private Integer businessId;

    @JsonProperty("productImg")
    @ApiModelProperty(value = "产品图片", example = "91fa53e0010c404c8bea8be379f36e00", required = true)
    private String productImg;

    @JsonProperty("productName")
    @ApiModelProperty(value = "产品名称", example = "一生情 一杯酒", required = true)
    private String productName;

    @JsonProperty("productPrice")
    @ApiModelProperty(value = "产品单价", example = "20000", required = true)
    private Integer productPrice;

    @JsonProperty("productCount")
    @ApiModelProperty(value = "产品数量", example = "3", required = true)
    private Integer productCount;

}
