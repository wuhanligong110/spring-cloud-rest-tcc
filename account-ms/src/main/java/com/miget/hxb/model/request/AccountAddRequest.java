package com.miget.hxb.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author hxb
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class AccountAddRequest extends RestfulRequest{

    private static final long serialVersionUID = 5232108251925362095L;

    private Integer userId;

    private String userName;

    private String reserveMobile;

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "账户号", example = "6222023202018412030", required = true)
    private String accountCard;

    private String accountCode;

    private Integer accountType;

    private String accountName;

    private String city;

    private String openAccount;

    private String idCard;
}
