package com.github.prontera.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.prontera.domain.CrmUserAddress;
import com.github.prontera.model.request.AddressDeleteRequest;
import com.github.prontera.model.request.PageRequest;
import com.github.prontera.persistence.CrmUserAddressMapper;
import com.github.prontera.persistence.CrudMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public int addressDelete(AddressDeleteRequest request) {
        return mapper.addressDelete(request);
    }
}
