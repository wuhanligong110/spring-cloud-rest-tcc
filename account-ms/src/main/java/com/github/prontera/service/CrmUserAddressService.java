package com.github.prontera.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.prontera.Shift;
import com.github.prontera.controller.StatusCode;
import com.github.prontera.domain.CrmUser;
import com.github.prontera.domain.CrmUserAddress;
import com.github.prontera.event.RegisterAddCreditEvent;
import com.github.prontera.model.request.LoginRequest;
import com.github.prontera.model.request.PageRequest;
import com.github.prontera.model.request.RegisterRequest;
import com.github.prontera.model.response.LoginResponse;
import com.github.prontera.model.response.RegisterResponse;
import com.github.prontera.persistence.CrmUserAddressMapper;
import com.github.prontera.persistence.CrmUserMapper;
import com.github.prontera.persistence.CrudMapper;
import com.github.prontera.util.OrikaMapper;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.html.HtmlEscapers;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hxb
 */
@Service
public class CrmUserAddressService extends CrudServiceImpl<CrmUserAddress>{
    private static final Logger LOGGER = LoggerFactory.getLogger(CrmUserAddressService.class);

    @Autowired
    private CrmUserAddressMapper mapper;

    @Autowired
    public CrmUserAddressService(CrudMapper<CrmUserAddress> mapper) {
        super(mapper);
    }

    public Page<CrmUserAddress> queryUserAddressPageList(Long userId,PageRequest request) {
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        return mapper.queryUserAddressPageList(userId);
    }
}
