package com.miget.hxb.domain;

import com.miget.hxb.model.BasicDomain;
import java.time.OffsetDateTime;

public class SysBusinessWeixinConfig extends BasicDomain {
    private Long id;

    private Long businessId;

    private String appId;

    private String appSecret;

    private String mchId;

    private String payKey;

    private String keyPath;

    private String notifyUrl;

    private String token;

    private String expireseconds;

    private String indexPageUrl;

    private String templetType;

    private String fromUserName;

    private String remark;

    private OffsetDateTime createTime;

    private OffsetDateTime updateTime;

    private String operator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpireseconds() {
        return expireseconds;
    }

    public void setExpireseconds(String expireseconds) {
        this.expireseconds = expireseconds;
    }

    public String getIndexPageUrl() {
        return indexPageUrl;
    }

    public void setIndexPageUrl(String indexPageUrl) {
        this.indexPageUrl = indexPageUrl;
    }

    public String getTempletType() {
        return templetType;
    }

    public void setTempletType(String templetType) {
        this.templetType = templetType;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(OffsetDateTime createTime) {
        this.createTime = createTime;
    }

    public OffsetDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(OffsetDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}