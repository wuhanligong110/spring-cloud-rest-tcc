package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.SysConfig;
import com.miget.hxb.model.request.ConfigRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface SysConfigMapper extends CrudMapper<SysConfig> {

    SysConfig queryConfigByBnIdAndKey(@Param("businessId") Long businessId, @Param("configKey") String configKey);

    List<SysConfig> getValuesByBnIdAndType(ConfigRequest request);
}