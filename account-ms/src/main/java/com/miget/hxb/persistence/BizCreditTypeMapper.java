package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.BizCreditType;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface BizCreditTypeMapper extends CrudMapper<BizCreditType> {

    BizCreditType queryUseableCreditType(Integer typeValue);
}