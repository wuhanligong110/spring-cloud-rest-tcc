package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.EventSubscriber;
import com.miget.hxb.domain.type.EventStatus;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface EventSubscriberMapper extends CrudMapper<EventSubscriber> {


    int updateEventStatusByPrimaryKeyInCasMode(@Param("id") Long id, @Param("expect") EventStatus expect, @Param("target") EventStatus target);
}