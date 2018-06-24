package com.miget.hxb.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class UserCreditDetailRequest extends PageRequest{
    private static final long serialVersionUID = -2497495945885360764L;

    @JsonProperty("userId")
    @ApiModelProperty(value = "用户Id", example = "1000001", required = true)
    private Integer userId;

    @JsonProperty("typeValue")
    @ApiModelProperty(value = "积分类型", example = "1", required = true)
    private Integer typeValue;

    @JsonProperty("typeCategory")
    @ApiModelProperty(value = "积分分类", example = "1", required = true)
    private Integer typeCategory;
}
