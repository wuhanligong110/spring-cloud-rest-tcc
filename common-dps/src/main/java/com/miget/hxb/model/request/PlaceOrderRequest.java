package com.miget.hxb.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hxb
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class PlaceOrderRequest extends RestfulRequest {

    private static final long serialVersionUID = -7089168357959601473L;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "用户ID", required = true, example = "1")
    private Long userId;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "地址ID", required = true, example = "5")
    private Integer addressId;

    @ApiModelProperty(value = "订单明细", required = true)
    private List<PlaceOrderItemRequest> orderItems;

    @NotEmpty
    @ApiModelProperty(value = "订单ID", required = true, example = "xxxxxxxxxxxxx")
    private String outTradeNo;

}
