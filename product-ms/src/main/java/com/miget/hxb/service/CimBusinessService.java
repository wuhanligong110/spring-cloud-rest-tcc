package com.miget.hxb.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.miget.hxb.domain.CimBusiness;
import com.miget.hxb.model.request.PageRequest;
import com.miget.hxb.persistence.CimBusinessMapper;
import com.miget.hxb.persistence.CrudMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hxb
 */
@Service
public class CimBusinessService extends CrudServiceImpl<CimBusiness>{
    private static final Logger LOGGER = LoggerFactory.getLogger(CimBusinessService.class);

    @Autowired
    private CimBusinessMapper mapper;

    @Autowired
    public CimBusinessService(CrudMapper<CimBusiness> mapper) {
        super(mapper);
    }

    public Page<CimBusiness> findRecommend(PageRequest request) {
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        return mapper.findRecommend();
    }
}
