package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.BizOrderItem;
import com.miget.hxb.model.request.PaymentRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface BizOrderItemMapper extends CrudMapper<BizOrderItem> {

    int batchInsert(@Param("orderItemList") List<BizOrderItem> orderItemList);

    List<BizOrderItem> orderDetailList(@Param("orderId")Long orderId);

    int payConfirm(PaymentRequest request);
}