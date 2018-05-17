package com.miget.hxb.persistence;

import com.github.pagehelper.Page;
import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.BizOrder;
import com.miget.hxb.model.request.PaymentRequest;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface BizOrderMapper extends CrudMapper<BizOrder> {

    int payConfirm(PaymentRequest request);

    Page<BizOrder> userOrderPageList(@Param("userId") Long userId);

    Page<BizOrder> orderPageList(@Param("businessId")Integer businessId);
}