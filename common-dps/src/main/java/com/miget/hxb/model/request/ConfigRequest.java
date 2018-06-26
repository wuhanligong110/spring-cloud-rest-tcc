package com.miget.hxb.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class ConfigRequest implements Request{

    private static final long serialVersionUID = 7497653927161306513L;

    @JsonProperty("configKey")
    @ApiModelProperty(value = "配置键值", required = true, example = "enchashment_type")
    private String configKey;

    @JsonProperty("configType")
    @ApiModelProperty(value = "配置类型", required = true, example = "enchashment_config")
    private String configType;

    @JsonProperty("businessId")
    @ApiModelProperty(value = "商家ID", required = true, example = "1")
    private Long businessId;
}
