package com.miget.hxb.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.miget.hxb.domain.CrmUserAddress;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class OrderDetailResponse extends OrderListResponse {
    private static final long serialVersionUID = -6113615255825866969L;

    @JsonProperty("courierCompany")
    @ApiModelProperty(value = "快递公司", example = "yunda", required = true)
    private String courierCompany;

    @JsonProperty("courierNumber")
    @ApiModelProperty(value = "快递单号", example = "123456", required = true)
    private String courierNumber;

    @JsonProperty("addressId")
    @ApiModelProperty(value = "收件地址ID", example = "11", required = true)
    private Integer addressId;

    @JsonProperty("address")
    @ApiModelProperty(value = "地址", example = "湖北省武汉市洪山区XXXXXX", required = true)
    private CrmUserAddress address;

    @JsonProperty("createTime")
    @ApiModelProperty(value = "创建时间", example = "2018-01-01 00:00:00", required = true)
    private String createTime;

    @JsonProperty("payType")
    @ApiModelProperty(value = "支付方式", example = "微信支付", required = true)
    private String payType;

}
