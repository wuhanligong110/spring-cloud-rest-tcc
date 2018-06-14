package com.miget.hxb.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author hxb
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class PlaceOrderItemRequest extends RestfulRequest {

    private static final long serialVersionUID = -7089168357959601473L;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "产品ID", required = true, example = "1")
    private Integer productId;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "商家ID", required = true, example = "1")
    private Integer businessId;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "产品数量", required = true, example = "5")
    private Integer productCount;

}
