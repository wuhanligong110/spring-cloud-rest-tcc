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

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class ProductAddRequest extends RestfulRequest {
    private static final long serialVersionUID = -5179072770218493591L;

    @NotNull
    @Min(1)
    @JsonProperty("businessId")
    @ApiModelProperty(value = "商家Id", example = "1", required = true)
    private Integer businessId;

    @NotNull
    @Min(1)
    @JsonProperty("categotyId")
    @ApiModelProperty(value = "分类Id", example = "1", required = true)
    private Integer categotyId;

    @NotNull
    @Min(1)
    @JsonProperty("consumeType")
    @ApiModelProperty(value = "消费类型 1：积分购买 2：购物积分兑换", example = "1", required = true)
    private Integer consumeType;

    @NotNull
    @NotEmpty
    @JsonProperty("productName")
    @ApiModelProperty(value = "产品名称", example = "一生情", required = true)
    private String productName;

    @JsonProperty("subName")
    @ApiModelProperty(value = "产品名称--副标题", example = "一生情 一杯酒")
    private String subName;

    @NotNull
    @Min(1)
    @JsonProperty("prePrice")
    @ApiModelProperty(value = "原价", example = "100000", required = true)
    private Integer prePrice;

    @NotNull
    @Min(1)
    @JsonProperty("price")
    @ApiModelProperty(value = "现价", example = "95000", required = true)
    private Long price;

    @NotNull
    @Min(1)
    @JsonProperty("inventory")
    @ApiModelProperty(value = "产品总库存", example = "10000", required = true)
    private Integer inventory;

    @NotNull
    @Min(1)
    @JsonProperty("havaInventory")
    @ApiModelProperty(value = "已消耗库存", example = "998", required = true)
    private Integer havaInventory;

    @NotNull
    @NotEmpty
    @JsonProperty("listImg")
    @ApiModelProperty(value = "列表图片", example = "e2931de1f1a96f2afd593ee7aa8a7b79", required = true)
    private String listImg;

    @NotNull
    @NotEmpty
    @JsonProperty("detailImg")
    @ApiModelProperty(value = "详情图片", example = "cf6e35cb41b01d0e8bdc8011775911d4", required = true)
    private String detailImg;

    @NotNull
    @Min(0)
    @Max(1)
    @JsonProperty("ifHot")
    @ApiModelProperty(value = "是否热门产品  1-是  0-否", example = "1", required = true)
    private Integer ifHot;

    @NotNull
    @Min(0)
    @JsonProperty("sort")
    @ApiModelProperty(value = "排序", example = "15", required = true)
    private Integer sort;

    @JsonProperty("tag")
    @ApiModelProperty(value = "产品标签，逗号分隔", example = "实惠,打折", required = true)
    private String tag;

    @NotNull
    @NotEmpty
    @JsonProperty("productStandard")
    @ApiModelProperty(value = "产品规格", example = "食品名称# 海峡两岸金钻VIP一瓶|香型#酱香型 |净含量#500ml|配料#水.高粱.小麦|执行标准#GB/T26760-20（一级）|生产许可证号#QS11552038200143|生产日期#见瓶颈|贮存条件#常温保存|生产企业#贵州茅台酒厂(集团）保健酒业有限公司|厂址#贵州仁怀市茅台镇|产地#贵州遵义市|温馨提示#过量饮酒，有害健康", required = true)
    private String productStandard;

}
