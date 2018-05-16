package com.miget.hxb.domain;

import com.miget.hxb.model.BasicDomain;
import java.time.OffsetDateTime;

public class CimProduct extends BasicDomain {
    private Long productId;

    private Integer businessId;

    private Integer categotyId;

    private Integer consumeType;

    private String productName;

    private String subName;

    private Integer prePrice;

    private Long price;

    private Integer inventory;

    private Integer havaInventory;

    private String listImg;

    private String detailImg;

    private Integer ifHot;

    private Integer sort;

    private String tag;

    private OffsetDateTime createTime;

    private OffsetDateTime updateTime;

    private String operator;

    private String productStandard;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getCategotyId() {
        return categotyId;
    }

    public void setCategotyId(Integer categotyId) {
        this.categotyId = categotyId;
    }

    public Integer getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(Integer consumeType) {
        this.consumeType = consumeType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(Integer prePrice) {
        this.prePrice = prePrice;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Integer getHavaInventory() {
        return havaInventory;
    }

    public void setHavaInventory(Integer havaInventory) {
        this.havaInventory = havaInventory;
    }

    public String getListImg() {
        return listImg;
    }

    public void setListImg(String listImg) {
        this.listImg = listImg;
    }

    public String getDetailImg() {
        return detailImg;
    }

    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg;
    }

    public Integer getIfHot() {
        return ifHot;
    }

    public void setIfHot(Integer ifHot) {
        this.ifHot = ifHot;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public String getProductStandard() {
        return productStandard;
    }

    public void setProductStandard(String productStandard) {
        this.productStandard = productStandard;
    }
}