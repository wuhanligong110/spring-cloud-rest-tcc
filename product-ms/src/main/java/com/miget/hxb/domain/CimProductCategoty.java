package com.miget.hxb.domain;

import com.miget.hxb.model.BasicDomain;
import java.time.OffsetDateTime;

public class CimProductCategoty extends BasicDomain {
    private Long categotyId;

    private Integer parentId;

    private String categotyName;

    private String categotyImg;

    private Integer categotyOrder;

    private Integer ifShow;

    private OffsetDateTime createTime;

    private OffsetDateTime updateTime;

    private String operator;

    public Long getCategotyId() {
        return categotyId;
    }

    public void setCategotyId(Long categotyId) {
        this.categotyId = categotyId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCategotyName() {
        return categotyName;
    }

    public void setCategotyName(String categotyName) {
        this.categotyName = categotyName;
    }

    public String getCategotyImg() {
        return categotyImg;
    }

    public void setCategotyImg(String categotyImg) {
        this.categotyImg = categotyImg;
    }

    public Integer getCategotyOrder() {
        return categotyOrder;
    }

    public void setCategotyOrder(Integer categotyOrder) {
        this.categotyOrder = categotyOrder;
    }

    public Integer getIfShow() {
        return ifShow;
    }

    public void setIfShow(Integer ifShow) {
        this.ifShow = ifShow;
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