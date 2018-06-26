package com.miget.hxb.persistence;

import com.github.pagehelper.Page;
import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.BizCreditDetail;
import com.miget.hxb.model.request.CreditStatusRequest;
import com.miget.hxb.model.request.UserCreditDetailRequest;
import com.miget.hxb.model.response.UserCreditDetailResponse;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface BizCreditDetailMapper extends CrudMapper<BizCreditDetail> {

    Page<UserCreditDetailResponse> queryUserCreditPageList(UserCreditDetailRequest request);

    int updataCreditStatus(CreditStatusRequest request);
}