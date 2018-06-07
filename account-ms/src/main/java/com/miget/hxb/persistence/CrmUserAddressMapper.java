package com.miget.hxb.persistence;

import com.github.pagehelper.Page;
import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.CrmUserAddress;
import com.miget.hxb.model.request.AddressDeleteRequest;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface CrmUserAddressMapper extends CrudMapper<CrmUserAddress> {

    Page<CrmUserAddress> queryUserAddressPageList(@Param("userId") Long userId);

    int addressDelete(AddressDeleteRequest request);

    CrmUserAddress queryUserDefaultAddress(@Param("userId") Long userId);

    int clearDefault(@Param("userId") Integer userId);
}