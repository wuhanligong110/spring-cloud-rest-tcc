package com.miget.hxb.persistence;

import com.github.pagehelper.Page;
import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.CimProduct;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface CimProductMapper extends CrudMapper<CimProduct> {

    Page<CimProduct> queryBusinessProductPageList(@Param("businessId") Long businessId);
}