package com.miget.hxb.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.miget.hxb.domain.CimProductCategoty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class SecondCategoty implements Response {

	private static final long serialVersionUID = -7873530244195894831L;
	/**
	 * 标签名称
	 */
	private String flagName;
	/**
	 * 分类列表
	 */
	private List<CimProductCategoty> categotyList;

}
