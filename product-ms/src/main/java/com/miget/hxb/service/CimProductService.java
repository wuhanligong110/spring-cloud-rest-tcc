package com.miget.hxb.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.miget.hxb.domain.CimProduct;
import com.miget.hxb.model.request.OrderCancelRequest;
import com.miget.hxb.model.request.PageRequest;
import com.miget.hxb.model.request.PlaceOrderItemRequest;
import com.miget.hxb.model.request.ProductCatePageRequest;
import com.miget.hxb.model.response.ProductDetailResponse;
import com.miget.hxb.persistence.CimProductMapper;
import com.miget.hxb.persistence.CrudMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hxb
 */
@Service
public class CimProductService extends CrudServiceImpl<CimProduct>{
    private static final Logger LOGGER = LoggerFactory.getLogger(CimProductService.class);

    @Autowired
    private CimProductMapper mapper;

    @Autowired
    public CimProductService(CrudMapper<CimProduct> mapper) {
        super(mapper);
    }

    public Page<CimProduct> queryBusinessProductPageList(Long businessId,PageRequest request) {
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        return mapper.queryBusinessProductPageList(businessId);
    }

    public Integer orderCancel(List<OrderCancelRequest> orderCancelRequests) {
        Preconditions.checkNotNull(orderCancelRequests);
        if(orderCancelRequests.size() > 0){
            return mapper.addProductInventory(orderCancelRequests);
        }
        return 0;
    }

    public Map<Integer, CimProduct> orderProductInventory(List<PlaceOrderItemRequest> preOrderRequests) {
        Preconditions.checkNotNull(preOrderRequests);
        if(preOrderRequests.size() > 0){
            int updateResult = mapper.orderProductInventory(preOrderRequests);
            if(updateResult > 0){
                List<Integer> productIds = new ArrayList<>();
                for(PlaceOrderItemRequest orderItemRequest : preOrderRequests){
                    productIds.add(orderItemRequest.getProductId());
                }
                return findProducts(productIds);
            }
        }
        return null;
    }

    public Map<Integer,CimProduct> findProducts(List<Integer> productIds) {
        Preconditions.checkNotNull(productIds);
        if(productIds.size() > 0){
            Map<Integer,CimProduct> resultMap = mapper.findProducts(productIds);
            return resultMap;
        }
        return null;
    }

    public Page<CimProduct> queryBusinessOnSaleProductPageList(Long businessId, PageRequest request) {
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        return mapper.queryBusinessOnSaleProductPageList(businessId);
    }

    public Page<CimProduct> queryHotProductPageList(Long businessId, PageRequest request) {
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        return mapper.queryHotProductPageList(businessId);
    }

    public Page<CimProduct> queryProductCatePageList(ProductCatePageRequest request) {
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        return mapper.queryProductCatePageList(request);
    }

    public ProductDetailResponse productDetail(Long productId) {
        Preconditions.checkNotNull(productId);
        return mapper.productDetail(productId);
    }
}
