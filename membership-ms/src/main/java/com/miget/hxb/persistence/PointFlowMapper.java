package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.PointFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface PointFlowMapper extends CrudMapper<PointFlow> {

    List<PointFlow> selectAllByUserId(@Param("userId") Long userId);

    PointFlow selectByOrderId(@Param("orderId") Long orderId);

}