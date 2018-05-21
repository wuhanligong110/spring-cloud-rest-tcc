package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.SmAdvertisement;
import com.miget.hxb.model.request.AdvRequest;

import java.util.List;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface SmAdvertisementMapper extends CrudMapper<SmAdvertisement> {

    List<SmAdvertisement> queryAdvsPageList(AdvRequest request);

}