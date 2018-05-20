package com.miget.hxb.persistence;

import com.github.pagehelper.Page;
import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.CimProduct;
import com.miget.hxb.model.request.OrderCancelRequest;
import com.miget.hxb.model.request.PlaceOrderItemRequest;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface CimProductMapper extends CrudMapper<CimProduct> {

    Page<CimProduct> queryBusinessProductPageList(@Param("businessId") Long businessId);

    Integer addProductInventory(List<OrderCancelRequest> orderCancelRequests);

    Integer orderProductInventory(List<PlaceOrderItemRequest> preOrderRequests);

    @MapKey("productId")
    Map<Integer,CimProduct> findProducts(List<Integer> productIds);
}