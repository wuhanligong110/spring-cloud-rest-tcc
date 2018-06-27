package com.miget.hxb.service;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.html.HtmlEscapers;
import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.event.RegisterAddCreditEvent;
import com.miget.hxb.model.request.CreditAddRequest;
import com.miget.hxb.model.request.CreditSubRequest;
import com.miget.hxb.model.request.LoginRequest;
import com.miget.hxb.model.request.RegisterRequest;
import com.miget.hxb.model.response.LoginResponse;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.model.response.RegisterResponse;
import com.miget.hxb.persistence.CrmUserMapper;
import com.miget.hxb.persistence.CrudMapper;
import com.miget.hxb.util.OrikaMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author hxb
 */
@Service
public class CrmUserService extends CrudServiceImpl<CrmUser> implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrmUserService.class);

    private ApplicationContext context;

    @Autowired
    private CrmUserMapper mapper;

    @Autowired
    public CrmUserService(CrudMapper<CrmUser> mapper) {
        super(mapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public RegisterResponse register(RegisterRequest request) {
        Preconditions.checkNotNull(request);
        final CrmUser dbUser = find(request.getMobile());
        if (dbUser != null) {
            Shift.fatal(StatusCode.USER_EXISTS);
        }
        // 重新计算密码
        final CrmUser transientUser = OrikaMapper.map(request, CrmUser.class);
        final String salt = generateRandomPasswordSalt();
        final String loginPassword = digestWithSalt(transientUser.getPassword(), salt);
        transientUser.setPwdSalt(salt);
        transientUser.setPassword(loginPassword);
        if(StringUtils.isEmpty(transientUser.getAncestor())){
            transientUser.setAncestor("0");
        }
        if(StringUtils.isEmpty(transientUser.getParentId())){
            transientUser.setParentId(0);
        }
        // 混合盐后入库
        persistNonNullProperties(transientUser);
        //赠送注册奖励--注册送20元现金
        context.publishEvent(new RegisterAddCreditEvent(transientUser));
        return OrikaMapper.map(transientUser, RegisterResponse.class);
    }

    public ObjectDataResponse<LoginResponse> login(LoginRequest request) {
        Preconditions.checkNotNull(request);
        final CrmUser user = find(request.getMobile());
        if (user == null) {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        // 登录用户的密码摘要
        final String requestLoginPWd = digestWithSalt(request.getLoginPwd(), user.getPwdSalt());
        if (!Objects.equal(requestLoginPWd, user.getPassword())) {
            Shift.fatal(StatusCode.INVALID_CREDENTIAL);
        }
        final LoginResponse response = new LoginResponse();
        response.setMobile(user.getMobile());
        response.setUserId(Long.valueOf(user.getUserId()));
        return new ObjectDataResponse<>(response);
    }

    public CrmUser find(String mobile) {
        Preconditions.checkNotNull(mobile);
        CrmUser result = null;
        if (!mobile.isEmpty()) {
            final String escapeMobile = HtmlEscapers.htmlEscaper().escape(mobile);
            result = mapper.queryByMobile(escapeMobile);
        }
        return result;
    }

    private String digestWithSalt(String content, String key) {
        String result = content;
        for (int i = 0; i < 5; i++) {
            result = DigestUtils.sha256Hex(result + key);
        }
        return result;
    }

    private String generateRandomPasswordSalt() {
        return DigestUtils.sha256Hex(String.valueOf(System.nanoTime()));
    }

    public CrmUser queryByUserId(Long userId) {
        Preconditions.checkNotNull(userId);
        return mapper.queryByUserId(userId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public int addWithdrawCredit(CreditAddRequest request) {
        return mapper.addWithdrawCredit(request);
    }

    public int addShopCredit(CreditAddRequest request) {
        return mapper.addShopCredit(request);
    }

    public CrmUser queryUserByOpenId(String openid) {
        Preconditions.checkNotNull(openid);
        return mapper.queryUserByOpenId(openid);
    }

    public int subWithdrawCredit(CreditSubRequest request) {
        return mapper.subWithdrawCredit(request);
    }

    public int subShopCredit(CreditSubRequest request) {
        return mapper.subShopCredit(request);
    }
}
