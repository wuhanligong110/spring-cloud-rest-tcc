package com.miget.hxb.service;

import com.google.common.base.Preconditions;
import com.miget.hxb.domain.BizOrderItem;
import com.miget.hxb.model.request.PaymentRequest;
import com.miget.hxb.persistence.BizOrderItemMapper;
import com.miget.hxb.persistence.CrudMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hxb
 */
@Service
public class BizOrderItemService extends CrudServiceImpl<BizOrderItem> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BizOrderItemService.class);

    @Resource
    private BizOrderItemMapper mapper;

    @Autowired
    public BizOrderItemService(CrudMapper<BizOrderItem> mapper) {
        super(mapper);
    }

    public int batchInsert(List<BizOrderItem> orderItemList) {
        return mapper.batchInsert(orderItemList);
    }

    public List<BizOrderItem> orderDetailList(Long orderId) {
        List<BizOrderItem> orderItems = mapper.orderDetailList(orderId);
        return  orderItems;
    }

    public int payConfirm(PaymentRequest request) {
        Preconditions.checkNotNull(request);
        return mapper.payConfirm(request);
    }
}
