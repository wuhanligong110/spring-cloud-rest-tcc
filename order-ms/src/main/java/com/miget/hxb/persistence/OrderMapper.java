package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.Order;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface OrderMapper extends CrudMapper<Order> {

}