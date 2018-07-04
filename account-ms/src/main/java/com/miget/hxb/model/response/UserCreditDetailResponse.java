package com.miget.hxb.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class UserCreditDetailResponse implements Response {
    private static final long serialVersionUID = -6113615255825866969L;

    @JsonProperty("orderId")
    @ApiModelProperty(value = "订单号", example = "91fa53e0010c404c8bea8be379f36e00", required = true)
    private String orderId;

    @JsonProperty("remark")
    @ApiModelProperty(value = "备注", example = "购物奖励1000积分", required = true)
    private String remark;

    @JsonProperty("credit")
    @ApiModelProperty(value = "积分", example = "10000", required = true)
    private Long credit;

    @JsonProperty("createTime")
    @ApiModelProperty(value = "创建时间", example = "2018-05-20 20:00:00", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private OffsetDateTime createTime;

    @JsonProperty("typeName")
    @ApiModelProperty(value = "类型名称", example = "购物奖励", required = true)
    private String typeName;

    @JsonProperty("typeValue")
    @ApiModelProperty(value = "类型值", example = "1", required = true)
    private Integer typeValue;

    @JsonProperty("typeCategory")
    @ApiModelProperty(value = "类型分类(1=可提现积分|2=购物积分)", example = "1", required = true)
    private Integer typeCategory;

    @JsonProperty("balanceType")
    @ApiModelProperty(value = "收支类型(1=收入|2=支出)", example = "1", required = true)
    private Integer balanceType;

}
