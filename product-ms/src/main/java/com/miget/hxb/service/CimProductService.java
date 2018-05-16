package com.miget.hxb.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.miget.hxb.domain.CimProduct;
import com.miget.hxb.model.request.PageRequest;
import com.miget.hxb.persistence.CimProductMapper;
import com.miget.hxb.persistence.CrudMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hxb
 */
@Service
public class CimProductService extends CrudServiceImpl<CimProduct>{
    private static final Logger LOGGER = LoggerFactory.getLogger(CimProductService.class);

    @Autowired
    private CimProductMapper mapper;

    @Autowired
    public CimProductService(CrudMapper<CimProduct> mapper) {
        super(mapper);
    }

    public Page<CimProduct> queryBusinessProductPageList(Long businessId,PageRequest request) {
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        return mapper.queryBusinessProductPageList(businessId);
    }

}
