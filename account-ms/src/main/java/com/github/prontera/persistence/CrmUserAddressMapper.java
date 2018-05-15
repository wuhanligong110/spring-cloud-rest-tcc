package com.github.prontera.persistence;

import com.github.pagehelper.Page;
import com.github.prontera.MyBatisRepository;
import com.github.prontera.domain.CrmUserAddress;
import com.github.prontera.model.request.AddressDeleteRequest;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface CrmUserAddressMapper extends CrudMapper<CrmUserAddress> {

    Page<CrmUserAddress> queryUserAddressPageList(@Param("userId") Long userId);

    int addressDelete(AddressDeleteRequest request);
}