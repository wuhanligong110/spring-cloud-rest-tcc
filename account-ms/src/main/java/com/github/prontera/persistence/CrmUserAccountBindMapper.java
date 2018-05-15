package com.github.prontera.persistence;

import com.github.pagehelper.Page;
import com.github.prontera.MyBatisRepository;
import com.github.prontera.domain.CrmUserAccountBind;
import com.github.prontera.model.request.AccountUnBindRequest;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface CrmUserAccountBindMapper extends CrudMapper<CrmUserAccountBind> {

    Page<CrmUserAccountBind> queryUserAccountPageList(@Param("userId") Long userId);

    int accountUnBind(AccountUnBindRequest request);
}