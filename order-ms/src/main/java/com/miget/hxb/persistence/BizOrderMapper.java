package com.miget.hxb.persistence;

import com.github.pagehelper.Page;
import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.BizOrder;
import com.miget.hxb.model.request.OrderStatusPageRequest;
import com.miget.hxb.model.request.PaymentRequest;
import com.miget.hxb.model.response.OrderDetailResponse;
import com.miget.hxb.model.response.OrderListResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface BizOrderMapper extends CrudMapper<BizOrder> {

    int payConfirm(PaymentRequest request);

    Page<BizOrder> orderPageList(@Param("businessId")Integer businessId);

    BizOrder queryOrderByTradeNo(@Param("outTradeNo")String outTradeNo);

    Page<BizOrder> queryUserOrderPageList(OrderStatusPageRequest request);

    List<OrderListResponse> userOrderList(List<Long> orderIdList);

    OrderDetailResponse orderDetail(@Param("orderId")Long orderId);
}