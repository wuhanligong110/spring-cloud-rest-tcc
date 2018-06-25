package com.miget.hxb.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class WithdrawRequest implements Request{

    @ApiModelProperty(value = "提现金额", required = true, example = "1000.00")
    private BigDecimal amount;

    @ApiModelProperty(value = "用户提现账户", required = true, example = "23")
    private Integer userAccount;

    @ApiModelProperty(value = "用户ID", required = true, example = "1000001")
    private Long userId;
}
