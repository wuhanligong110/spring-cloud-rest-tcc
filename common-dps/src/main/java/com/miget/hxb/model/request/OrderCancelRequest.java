package com.miget.hxb.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class OrderCancelRequest extends RestfulRequest{

    private static final long serialVersionUID = -4274907076944080062L;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "产品ID", required = true, example = "1")
    private Integer productId;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "产品数量", required = true, example = "1")
    private Integer productCount;
}
