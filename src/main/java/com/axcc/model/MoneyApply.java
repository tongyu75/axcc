package com.axcc.model;

import java.util.Date;

public class MoneyApply {
    private Integer id;

    private Integer userId;

    private Float applyMoney;

    private Date applyTime;

    private Integer userStatus;

    private Integer checkStatus;

    private Date createTime;

    private String description;

    private Integer level1Count;

    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(Float applyMoney) {
        this.applyMoney = applyMoney;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getLevel1Count() {
        return level1Count;
    }

    public void setLevel1Count(Integer level1Count) {
        this.level1Count = level1Count;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}