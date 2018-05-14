package com.github.prontera.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 分页请求
 * @author Administrator
 */
@Getter
@Setter
@NoArgsConstructor
public class PageRequest implements Request {

    private static final long serialVersionUID = 3822254586637939558L;

    private int pageNo = 1;

    private int pageSize = 10;
}
