package com.miget.hxb.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class ProductCategotyUpdateRequest extends ProductCategotyAddRequest {
    private static final long serialVersionUID = 1625654364318765606L;

    @NotNull
    @Min(1)
    @JsonProperty("categotyId")
    @ApiModelProperty(value = "产品分类ID", example = "2", required = true)
    private Long categotyId;
}
