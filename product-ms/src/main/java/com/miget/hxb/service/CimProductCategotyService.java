package com.miget.hxb.service;

import com.miget.hxb.domain.CimBusiness;
import com.miget.hxb.domain.CimProductCategoty;
import com.miget.hxb.persistence.CimBusinessMapper;
import com.miget.hxb.persistence.CimProductCategotyMapper;
import com.miget.hxb.persistence.CrudMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hxb
 */
@Service
public class CimProductCategotyService extends CrudServiceImpl<CimProductCategoty>{
    private static final Logger LOGGER = LoggerFactory.getLogger(CimProductCategotyService.class);

    @Autowired
    private CimProductCategotyMapper mapper;

    @Autowired
    public CimProductCategotyService(CrudMapper<CimProductCategoty> mapper) {
        super(mapper);
    }

    public List<CimProductCategoty> queryProductCategotyList(Integer level) {
        if(level == null){
            return mapper.findAll();
        }else if(level == 1){
            return  mapper.queryFirstLevel();
        }else if(level == 2){
            return  mapper.querySecondLevel();
        }else if(level == 3){
            return  mapper.queryThirdLevel();
        }
        return null;
    }
}
