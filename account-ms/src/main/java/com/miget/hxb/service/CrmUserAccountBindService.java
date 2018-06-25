package com.miget.hxb.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.miget.hxb.domain.CrmUserAccountBind;
import com.miget.hxb.model.request.AccountUnBindRequest;
import com.miget.hxb.model.request.PageRequest;
import com.miget.hxb.persistence.CrmUserAccountBindMapper;
import com.miget.hxb.persistence.CrudMapper;
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

    public CrmUserAccountBind queryUserAccountTypeInfo(Long userId, Integer accountType) {
        Preconditions.checkNotNull(userId);
        Preconditions.checkNotNull(accountType);
        return mapper.queryUserAccountTypeInfo(userId,accountType);
    }

    public CrmUserAccountBind queryUserAccountInfo(Long userId, Integer userAccount) {
        Preconditions.checkNotNull(userId);
        Preconditions.checkNotNull(userAccount);
        return mapper.queryUserAccountInfo(userId,userAccount);
    }
}
