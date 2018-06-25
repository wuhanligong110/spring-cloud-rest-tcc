package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.SysConfig;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface SysConfigMapper extends CrudMapper<SysConfig> {

    SysConfig queryConfigByBnIdAndKey(@Param("businessId") Long businessId, @Param("key") String key);

}