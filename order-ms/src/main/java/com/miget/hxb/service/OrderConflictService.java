package com.miget.hxb.service;

import com.miget.hxb.domain.OrderConflict;
import com.miget.hxb.persistence.CrudMapper;
import com.miget.hxb.persistence.CrudMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zhao Junjian
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class OrderConflictService extends CrudServiceImpl<OrderConflict> {

    public OrderConflictService(CrudMapper<OrderConflict> mapper) {
        super(mapper);
    }

}
