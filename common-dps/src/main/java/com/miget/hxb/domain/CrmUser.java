package com.miget.hxb.domain;

import com.miget.hxb.model.BasicDomain;

import java.time.OffsetDateTime;
import java.util.Date;

public class CrmUser extends BasicDomain {
    private Integer userId;

    private Integer businessId;

    private Integer parentId;

    private Integer vipLevel;

    private Long buyTotalMoney;

    private Long withdrawalCreditTotal;

    private Long haveWithdrawalCreditTotal;

    private Long accWithdrawalCredit;

    private Long shopCreditTotal;

    private Long usedShopCreditTotal;

    private Long accShopCredit;

    private String wechatNickname;

    private Integer wechatSubscribe;

    private String wechatOpenid;

    private Integer wechatSex;

    private String wechatCity;

    private String wechatCountry;

    private String wechatProvince;

    private String wechatLanguage;

    private String wechatHeadimgurl;

    private Date wechatSubscribeTime;

    private String wechatUnionid;

    private String wechatRemark;

    private String wechatGroupid;

    private Integer status;

    private String mobile;

    private String password;

    private OffsetDateTime createTime;

    private OffsetDateTime updateTime;

    private String operator;

    private String pwdSalt;

    private String ancestor;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Long getBuyTotalMoney() {
        return buyTotalMoney;
    }

    public void setBuyTotalMoney(Long buyTotalMoney) {
        this.buyTotalMoney = buyTotalMoney;
    }

    public Long getWithdrawalCreditTotal() {
        return withdrawalCreditTotal;
    }

    public void setWithdrawalCreditTotal(Long withdrawalCreditTotal) {
        this.withdrawalCreditTotal = withdrawalCreditTotal;
    }

    public Long getHaveWithdrawalCreditTotal() {
        return haveWithdrawalCreditTotal;
    }

    public void setHaveWithdrawalCreditTotal(Long haveWithdrawalCreditTotal) {
        this.haveWithdrawalCreditTotal = haveWithdrawalCreditTotal;
    }

    public Long getAccWithdrawalCredit() {
        return accWithdrawalCredit;
    }

    public void setAccWithdrawalCredit(Long accWithdrawalCredit) {
        this.accWithdrawalCredit = accWithdrawalCredit;
    }

    public Long getShopCreditTotal() {
        return shopCreditTotal;
    }

    public void setShopCreditTotal(Long shopCreditTotal) {
        this.shopCreditTotal = shopCreditTotal;
    }

    public Long getUsedShopCreditTotal() {
        return usedShopCreditTotal;
    }

    public void setUsedShopCreditTotal(Long usedShopCreditTotal) {
        this.usedShopCreditTotal = usedShopCreditTotal;
    }

    public Long getAccShopCredit() {
        return accShopCredit;
    }

    public void setAccShopCredit(Long accShopCredit) {
        this.accShopCredit = accShopCredit;
    }

    public String getWechatNickname() {
        return wechatNickname;
    }

    public void setWechatNickname(String wechatNickname) {
        this.wechatNickname = wechatNickname;
    }

    public Integer getWechatSubscribe() {
        return wechatSubscribe;
    }

    public void setWechatSubscribe(Integer wechatSubscribe) {
        this.wechatSubscribe = wechatSubscribe;
    }

    public String getWechatOpenid() {
        return wechatOpenid;
    }

    public void setWechatOpenid(String wechatOpenid) {
        this.wechatOpenid = wechatOpenid;
    }

    public Integer getWechatSex() {
        return wechatSex;
    }

    public void setWechatSex(Integer wechatSex) {
        this.wechatSex = wechatSex;
    }

    public String getWechatCity() {
        return wechatCity;
    }

    public void setWechatCity(String wechatCity) {
        this.wechatCity = wechatCity;
    }

    public String getWechatCountry() {
        return wechatCountry;
    }

    public void setWechatCountry(String wechatCountry) {
        this.wechatCountry = wechatCountry;
    }

    public String getWechatProvince() {
        return wechatProvince;
    }

    public void setWechatProvince(String wechatProvince) {
        this.wechatProvince = wechatProvince;
    }

    public String getWechatLanguage() {
        return wechatLanguage;
    }

    public void setWechatLanguage(String wechatLanguage) {
        this.wechatLanguage = wechatLanguage;
    }

    public String getWechatHeadimgurl() {
        return wechatHeadimgurl;
    }

    public void setWechatHeadimgurl(String wechatHeadimgurl) {
        this.wechatHeadimgurl = wechatHeadimgurl;
    }

    public Date getWechatSubscribeTime() {
        return wechatSubscribeTime;
    }

    public void setWechatSubscribeTime(Date wechatSubscribeTime) {
        this.wechatSubscribeTime = wechatSubscribeTime;
    }

    public String getWechatUnionid() {
        return wechatUnionid;
    }

    public void setWechatUnionid(String wechatUnionid) {
        this.wechatUnionid = wechatUnionid;
    }

    public String getWechatRemark() {
        return wechatRemark;
    }

    public void setWechatRemark(String wechatRemark) {
        this.wechatRemark = wechatRemark;
    }

    public String getWechatGroupid() {
        return wechatGroupid;
    }

    public void setWechatGroupid(String wechatGroupid) {
        this.wechatGroupid = wechatGroupid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPwdSalt() {
        return pwdSalt;
    }

    public void setPwdSalt(String pwdSalt) {
        this.pwdSalt = pwdSalt;
    }

    public String getAncestor() {
        return ancestor;
    }

    public void setAncestor(String ancestor) {
        this.ancestor = ancestor;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }
}