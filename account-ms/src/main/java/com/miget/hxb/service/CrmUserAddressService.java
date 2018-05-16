package com.miget.hxb.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.miget.hxb.domain.CrmUserAddress;
import com.miget.hxb.model.request.AddressDeleteRequest;
import com.miget.hxb.model.request.PageRequest;
import com.miget.hxb.persistence.CrmUserAddressMapper;
import com.miget.hxb.persistence.CrudMapper;
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
