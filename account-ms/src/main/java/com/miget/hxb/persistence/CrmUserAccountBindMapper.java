package com.miget.hxb.persistence;

import com.github.pagehelper.Page;
import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.CrmUserAccountBind;
import com.miget.hxb.model.request.AccountUnBindRequest;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface CrmUserAccountBindMapper extends CrudMapper<CrmUserAccountBind> {

    Page<CrmUserAccountBind> queryUserAccountPageList(@Param("userId") Long userId);

    int accountUnBind(AccountUnBindRequest request);

    CrmUserAccountBind queryUserAccountTypeInfo(@Param("userId")Long userId, @Param("accountType")Integer accountType);

    CrmUserAccountBind queryUserAccountInfo(@Param("userId")Long userId, @Param("userAccount")Integer userAccount);
}