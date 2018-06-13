package com.miget.hxb.service;

import com.miget.hxb.domain.SysBusinessWeixinConfig;
import com.miget.hxb.persistence.CrudMapper;
import com.miget.hxb.persistence.SysBusinessWeixinConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
*
* @描述：SysBusinessWeixinConfigService 服务实现类
*
* @创建人： hxb
*
* @创建时间：2017年03月08日 17:14:05
*
* Copyright (c) 深圳米格网络科技有限公司-版权所有
*/
@Service
public class SysBusinessWeixinConfigService extends CrudServiceImpl<SysBusinessWeixinConfig>{

   private static final Logger LOGGER = LoggerFactory.getLogger(SysBusinessWeixinConfigService.class);

   @Autowired
   private SysBusinessWeixinConfigMapper mapper;

   @Autowired
   public SysBusinessWeixinConfigService(CrudMapper<SysBusinessWeixinConfig> mapper) {
       super(mapper);
   }

   public SysBusinessWeixinConfig queryBusinessWeixinConfig(Long businessId) {
        return mapper.queryBusinessWeixinConfig(businessId);
   }
}
