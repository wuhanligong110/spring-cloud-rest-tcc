package com.miget.hxb.service;

import com.google.common.base.Preconditions;
import com.miget.hxb.domain.SmAdvertisement;
import com.miget.hxb.model.request.AdvRequest;
import com.miget.hxb.persistence.CrudMapper;
import com.miget.hxb.persistence.SmAdvertisementMapper;
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
public class SmAdvertisementService extends CrudServiceImpl<SmAdvertisement>{

   private static final Logger LOGGER = LoggerFactory.getLogger(SmAdvertisementService.class);

   @Autowired
   private SmAdvertisementMapper mapper;

   @Autowired
   public SmAdvertisementService(CrudMapper<SmAdvertisement> mapper) {
        super(mapper);
    }

   public List<SmAdvertisement> queryAdvsPageList(AdvRequest request) {
       Preconditions.checkNotNull(request);
       return mapper.queryAdvsPageList(request);
   }
}
