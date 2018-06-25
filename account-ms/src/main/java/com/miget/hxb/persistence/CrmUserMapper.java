package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.model.request.CreditAddRequest;
import com.miget.hxb.model.request.CreditSubRequest;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface CrmUserMapper extends CrudMapper<CrmUser> {

    CrmUser queryByMobile(@Param("mobile")String escapeMobile);

    CrmUser queryByUserId(@Param("userId")Long userId);

    int addWithdrawCredit(CreditAddRequest request);

    int addShopCredit(CreditAddRequest request);

    CrmUser queryUserByOpenId(@Param("openid")String openid);

    int subWithdrawCredit(CreditSubRequest request);

    int subShopCredit(CreditSubRequest request);
}