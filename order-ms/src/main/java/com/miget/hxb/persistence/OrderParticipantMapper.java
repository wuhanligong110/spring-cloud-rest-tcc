package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.OrderParticipant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface OrderParticipantMapper extends CrudMapper<OrderParticipant> {

    List<OrderParticipant> selectByOrderId(@Param("orderId") Long orderId);
}