package com.miget.hxb.service;

import com.google.common.base.Preconditions;
import com.miget.hxb.domain.WxAccessToken;
import com.miget.hxb.persistence.CrudMapper;
import com.miget.hxb.persistence.WxAccessTokenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class WxAccessTokenService extends CrudServiceImpl<WxAccessToken>{

   private static final Logger LOGGER = LoggerFactory.getLogger(WxAccessTokenService.class);

   @Autowired
   private WxAccessTokenMapper mapper;

   @Autowired
   public WxAccessTokenService(CrudMapper<WxAccessToken> mapper) {
        super(mapper);
    }

   public WxAccessToken selectNewAccessToken(Long businessId) {
       Preconditions.checkNotNull(businessId);
       return mapper.selectNewAccessToken(businessId);
   }

}
