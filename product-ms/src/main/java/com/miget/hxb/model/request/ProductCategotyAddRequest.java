package com.miget.hxb.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
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
public class ProductCategotyAddRequest extends RestfulRequest {

    private static final long serialVersionUID = 6307628976409957106L;

    @JsonProperty("parentId")
    @ApiModelProperty(value = "上级分类Id", example = "1")
    private Integer parentId;

    @NotNull
    @NotEmpty
    @JsonProperty("categotyName")
    @ApiModelProperty(value = "产品分类名称", example = "白酒", required = true)
    private String categotyName;

    @NotNull
    @NotEmpty
    @JsonProperty("categotyImg")
    @ApiModelProperty(value = "产品分类图片", example = "cf6e35cb41b01d0e8bdc8011775911d4", required = true)
    private String categotyImg;

    @NotNull
    @Min(1)
    @JsonProperty("categotyOrder")
    @ApiModelProperty(value = "产品分类排序", example = "2", required = true)
    private Integer categotyOrder;

    @NotNull
    @Min(0)
    @Max(1)
    @JsonProperty("ifShow")
    @ApiModelProperty(value = "是否显示 0-不显示 1-显示", example = "1", required = true)
    private Integer ifShow;
}
