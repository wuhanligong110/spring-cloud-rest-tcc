package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.PointSummary;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface PointSummaryMapper extends CrudMapper<PointSummary> {
    PointSummary selectByUserId(@Param("userId") Long userId);

    int increasePointByUserId(@Param("amount") int amount, @Param("userId") Long userId);
}