package com.miget.hxb.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页请求
 * @author Administrator
 */
@Getter
@Setter
@NoArgsConstructor
public class PageRequest implements Request {

    private static final long serialVersionUID = 3822254586637939558L;

    @NotNull
    @Min(1)
    private int pageNo = 1;

    @NotNull
    @Min(1)
    private int pageSize = 10;
}
