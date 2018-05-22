package com.miget.hxb.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.miget.hxb.domain.CimProductCategoty;
import com.miget.hxb.model.request.PageRequest;
import com.miget.hxb.model.response.SecondCategoty;
import com.miget.hxb.model.response.SecondCategotyResponse;
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

    public Page<CimProductCategoty> findRecommend(PageRequest request) {
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        return mapper.findRecommend();
    }

    public SecondCategotyResponse querySecondCategoty(Integer categotyId) {
        SecondCategotyResponse response = new SecondCategotyResponse();
        CimProductCategoty categoty = find(Long.valueOf(categotyId));
        if(categoty != null){
            response.setCategoryHeader(categoty.getCategotyImg());
            List<CimProductCategoty> secondCategotyList = queryChildrenCategotyList(categotyId);
            Preconditions.checkNotNull(secondCategotyList);
            if(secondCategotyList.size() > 0){
                List<SecondCategoty> secondCategotys = Lists.newArrayListWithCapacity(secondCategotyList.size());
                for(CimProductCategoty temp : secondCategotyList){
                    SecondCategoty tempCate = new SecondCategoty();
                    tempCate.setFlagName(temp.getCategotyName());
                    tempCate.setCategotyList(queryChildrenCategotyList(Math.toIntExact(temp.getCategotyId())));
                    secondCategotys.add(tempCate);
                }
                response.setSecondCategotyList(secondCategotys);
            }
            return response;
        }
        return null;
    }

    private List<CimProductCategoty> queryChildrenCategotyList(Integer categotyId) {
        Preconditions.checkNotNull(categotyId);
        return mapper.queryChildrenCategotyList(categotyId);
    }
}
