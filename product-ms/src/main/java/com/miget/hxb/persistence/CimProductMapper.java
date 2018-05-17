package com.miget.hxb.persistence;

import com.github.pagehelper.Page;
import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.CimProduct;
import com.miget.hxb.model.request.OrderCancelRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface CimProductMapper extends CrudMapper<CimProduct> {

    Page<CimProduct> queryBusinessProductPageList(@Param("businessId") Long businessId);

    Integer addProductInventory(List<OrderCancelRequest> orderCancelRequests);
}