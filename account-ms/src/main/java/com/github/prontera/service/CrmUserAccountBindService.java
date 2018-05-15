package com.github.prontera.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.prontera.domain.CrmUserAccountBind;
import com.github.prontera.model.request.AccountUnBindRequest;
import com.github.prontera.model.request.PageRequest;
import com.github.prontera.persistence.CrmUserAccountBindMapper;
import com.github.prontera.persistence.CrudMapper;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hxb
 */
@Service
public class CrmUserAccountBindService extends CrudServiceImpl<CrmUserAccountBind>{
    private static final Logger LOGGER = LoggerFactory.getLogger(CrmUserAccountBindService.class);

    @Autowired
    private CrmUserAccountBindMapper mapper;

    @Autowired
    public CrmUserAccountBindService(CrudMapper<CrmUserAccountBind> mapper) {
        super(mapper);
    }

    public Page<CrmUserAccountBind> queryUserAccountPageList(Long userId,PageRequest request) {
        Preconditions.checkNotNull(userId);
        Preconditions.checkNotNull(request);
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        return mapper.queryUserAccountPageList(userId);
    }

    public int accountUnBind(AccountUnBindRequest request) {
        Preconditions.checkNotNull(request);
        return mapper.accountUnBind(request);
    }
}
