package com.miget.hxb.service;

import com.miget.hxb.domain.SysConfig;
import com.miget.hxb.model.request.ConfigRequest;
import com.miget.hxb.persistence.CrudMapper;
import com.miget.hxb.persistence.SysConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*
* @描述：WxAccessTokenService 服务实现类
*
* @创建人： liqimoon
*
* @创建时间：2017年03月08日 17:14:05
*
* Copyright (c) 深圳米格网络科技有限公司-版权所有
*/
@Service
public class SysConfigService extends CrudServiceImpl<SysConfig>{

   private static final Logger LOGGER = LoggerFactory.getLogger(SysConfigService.class);

   @Autowired
   private SysConfigMapper mapper;

   @Autowired
   public SysConfigService(CrudMapper<SysConfig> mapper) {
        super(mapper);
    }

    
   public boolean getSwitching(Long businessId,String key) {
       SysConfig result = mapper.queryConfigByBnIdAndKey(businessId,key);
       if(result.getConfigValue().equals("true")){
           return true;
       }
       return false;
   }

   public Integer getIntegerValueByKey(Long businessId,String key) {
       SysConfig result = mapper.queryConfigByBnIdAndKey(businessId,key);
       return Integer.parseInt(result.getConfigValue());
   }

   public String getValuesByBnIdAndKey(Long businessId,String key) {
       SysConfig result = mapper.queryConfigByBnIdAndKey(businessId,key);
       return result.getConfigValue();
   }

    public List<SysConfig> getValuesByBnIdAndType(ConfigRequest request) {
        return mapper.getValuesByBnIdAndType(request);
    }
}
