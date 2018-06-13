package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.SysBusinessWeixinConfig;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface SysBusinessWeixinConfigMapper extends CrudMapper<SysBusinessWeixinConfig> {

    SysBusinessWeixinConfig queryBusinessWeixinConfig(@Param("businessId") Long businessId);

}