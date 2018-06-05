package com.miget.hxb.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class AddressAddRequest extends RestfulRequest{

    private static final long serialVersionUID = 1369875943024499640L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("userId")
    private Integer userId;

    @NotNull
    @ApiModelProperty(value = "收件人姓名", example = "张三", required = true)
    @JsonProperty("receivingUserName")
    private String receivingUserName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\d{11}$", message = "请输入11位手机号")
    @ApiModelProperty(value = "收件人联系电话", example = "15222222222", required = true)
    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("provinceName")
    private String provinceName;

    @JsonProperty("cityName")
    private String cityName;

    @JsonProperty("area")
    private String area;

    @JsonProperty("receivingAddress")
    private String receivingAddress;

    @JsonProperty("thirdAccount")
    private String thirdAccount;

    @NotNull
    @ApiModelProperty(value = "地址类型", example = "1", required = true)
    @JsonProperty("type")
    private Integer type;

    @NotNull
    @ApiModelProperty(value = "地址类型名称", example = "收件地址", required = true)
    @JsonProperty("typeName")
    private String typeName;
}
