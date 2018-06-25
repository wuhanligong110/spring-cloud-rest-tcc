package com.miget.hxb.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class CreditAddRequest extends RestfulRequest{
    private static final long serialVersionUID = -8143037479585363948L;

    @JsonProperty("userId")
    @ApiModelProperty(value = "用户Id", example = "1000001", required = true)
    private Integer userId;

    @NotNull
    @JsonProperty("orderId")
    @ApiModelProperty(value = "订单号", example = "91fa53e0010c404c8bea8be379f36e00", required = true)
    private String orderId;

    @NotNull
    @JsonProperty("remark")
    @ApiModelProperty(value = "备注", example = "购物奖励1000积分", required = true)
    private String remark;

    @NotNull
    @Min(1)
    @JsonProperty("credit")
    @ApiModelProperty(value = "积分", example = "10000", required = true)
    private Long credit;

    @NotNull
    @Min(1)
    @JsonProperty("typeValue")
    @ApiModelProperty(value = "类型值", example = "1", required = true)
    private Integer typeValue;
}
