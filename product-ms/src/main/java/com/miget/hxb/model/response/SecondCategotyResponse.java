package com.miget.hxb.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class SecondCategotyResponse extends RestfulResponse  {

	private static final long serialVersionUID = 4347503935430551064L;

	/**
	 * 分类头图像
	 */
	private String categoryHeader;
	/**
	 * 二级分类标签列表
	 */
	private List<SecondCategoty> secondCategotyList;

}
