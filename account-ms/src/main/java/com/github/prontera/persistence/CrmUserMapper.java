package com.github.prontera.persistence;

import com.github.prontera.MyBatisRepository;
import com.github.prontera.domain.CrmUser;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface CrmUserMapper extends CrudMapper<CrmUser> {

    CrmUser queryByMobile(@Param("mobile")String escapeMobile);

    CrmUser queryByUserId(@Param("userId")Long userId);
}