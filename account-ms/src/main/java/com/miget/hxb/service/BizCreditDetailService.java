package com.miget.hxb.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.miget.hxb.domain.BizCreditDetail;
import com.miget.hxb.domain.BizCreditType;
import com.miget.hxb.model.request.CreditAddRequest;
import com.miget.hxb.model.request.CreditStatusRequest;
import com.miget.hxb.model.request.CreditSubRequest;
import com.miget.hxb.model.request.UserCreditDetailRequest;
import com.miget.hxb.model.response.UserCreditDetailResponse;
import com.miget.hxb.persistence.BizCreditDetailMapper;
import com.miget.hxb.persistence.CrudMapper;
import com.miget.hxb.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hxb
 */
@Service
public class BizCreditDetailService extends CrudServiceImpl<BizCreditDetail>{
    private static final Logger LOGGER = LoggerFactory.getLogger(BizCreditDetailService.class);

    @Resource
    private BizCreditDetailMapper mapper;

    @Resource
    private BizCreditTypeService creditTypeService;

    @Resource
    private CrmUserService userService;

    @Autowired
    public BizCreditDetailService(CrudMapper<BizCreditDetail> mapper) {
        super(mapper);
    }

    public Page<UserCreditDetailResponse> queryUserCreditPageList(UserCreditDetailRequest request) {
        Preconditions.checkNotNull(request);
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        return mapper.queryUserCreditPageList(request);
    }

    @Transactional
    public int creditAdd(CreditAddRequest request) {
        Preconditions.checkNotNull(request);
        if(request.getTypeValue() != null){
            BizCreditType creditType = creditTypeService.queryUseableCreditType(request.getTypeValue());
            Preconditions.checkNotNull(creditType);
            //积分类型分类为1：可提现积分 2：购物积分 时，需要更新关联的用户信息表上对应字段
            if(creditType.getTypeCategory() == 1){
                userService.addWithdrawCredit(request);
            }else if(creditType.getTypeCategory() == 2){
                userService.addShopCredit(request);
            }
            BizCreditDetail creditDetail = new BizCreditDetail();
            BeanUtils.copyProperties(request,creditDetail);
            creditDetail.setDealId(StringUtils.getUUID());
            creditDetail.setCreditType(request.getTypeValue());
            creditDetail.setOperator("system");
            return persistNonNullProperties(creditDetail);
        }
        return 0;
    }

    @Transactional
    public int creditBatchAdd(List<CreditAddRequest> requests) {
        Preconditions.checkNotNull(requests);
        if(requests.size() > 0){
            for(CreditAddRequest request : requests){
                //TODO 数据库操作太频繁，后续优化--批量操作
                creditAdd(request);
            }
            return 1;
        }
        return 0;
    }

    public int creditSub(CreditSubRequest request) {
        Preconditions.checkNotNull(request);
        if(request.getTypeValue() != null){
            BizCreditType creditType = creditTypeService.queryUseableCreditType(request.getTypeValue());
            Preconditions.checkNotNull(creditType);
            //积分类型分类为1：可提现积分 2：购物积分 时，需要更新关联的用户信息表上对应字段
            if(creditType.getTypeCategory() == 1){
                userService.subWithdrawCredit(request);
            }else if(creditType.getTypeCategory() == 2){
                userService.subShopCredit(request);
            }
            BizCreditDetail creditDetail = new BizCreditDetail();
            BeanUtils.copyProperties(request,creditDetail);
            creditDetail.setDealId(StringUtils.getUUID());
            creditDetail.setCreditType(request.getTypeValue());
            creditDetail.setOperator("system");
            return persistNonNullProperties(creditDetail);
        }
        return 0;
    }

    public int updataCreditStatus(CreditStatusRequest request) {
        return mapper.updataCreditStatus(request);
    }
}
