package com.miget.hxb.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Zhao Junjian
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class LoginResponse implements Response {
    private static final long serialVersionUID = 7883775815440213351L;

    @JsonProperty("mobile")
    @ApiModelProperty(value = "手机号", example = "18888888888", required = true)
    private String mobile;

    @JsonProperty("userId")
    @ApiModelProperty(value = "用户ID", example = "1000001", required = true)
    private Long userId;

}
