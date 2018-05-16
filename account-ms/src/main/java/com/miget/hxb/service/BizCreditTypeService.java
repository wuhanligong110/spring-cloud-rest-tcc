package com.miget.hxb.service;

import com.miget.hxb.domain.BizCreditType;
import com.miget.hxb.persistence.BizCreditTypeMapper;
import com.miget.hxb.persistence.CrudMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hxb
 */
@Service
public class BizCreditTypeService extends CrudServiceImpl<BizCreditType>{
    private static final Logger LOGGER = LoggerFactory.getLogger(BizCreditTypeService.class);

    @Autowired
    private BizCreditTypeMapper mapper;

    @Autowired
    public BizCreditTypeService(CrudMapper<BizCreditType> mapper) {
        super(mapper);
    }

    public BizCreditType queryUseableCreditType(Integer typeValue) {
        return mapper.queryUseableCreditType(typeValue);
    }
}
