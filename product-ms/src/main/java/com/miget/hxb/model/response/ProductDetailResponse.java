package com.miget.hxb.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

/**
 * @author Administrator
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class ProductDetailResponse implements Response{

    private static final long serialVersionUID = -293152878576204734L;

    private Integer businessId;

    private String businessName;

    private String logo;

    private Integer productId;

    private String productName;

    private Long price;

    private Integer isFork;

    private List<String> detailImgs;

    private String listImg;

}
