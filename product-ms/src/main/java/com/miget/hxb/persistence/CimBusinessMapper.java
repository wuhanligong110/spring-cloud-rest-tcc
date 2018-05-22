package com.miget.hxb.persistence;

import com.github.pagehelper.Page;
import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.CimBusiness;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface CimBusinessMapper extends CrudMapper<CimBusiness> {

    Page<CimBusiness> findRecommend();
}