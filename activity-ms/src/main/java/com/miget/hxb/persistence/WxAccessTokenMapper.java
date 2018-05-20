package com.miget.hxb.persistence;

import com.miget.hxb.domain.WxAccessToken;
import com.miget.hxb.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface WxAccessTokenMapper extends CrudMapper<WxAccessToken> {

    WxAccessToken selectNewAccessToken(@Param("businessId") Long businessId);
}