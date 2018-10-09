package com.axcc.model;

import java.util.Date;

public class History {
    private Integer id;

    private Integer userId;

    private Integer userType;

    private Float buyMoney;

    private Integer buyType;

    private Integer buyStatus;

    private Float agentMeMoney;

    private Date createTime;

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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Float getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(Float buyMoney) {
        this.buyMoney = buyMoney;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public Integer getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(Integer buyStatus) {
        this.buyStatus = buyStatus;
    }

    public Float getAgentMeMoney() {
        return agentMeMoney;
    }

    public void setAgentMeMoney(Float agentMeMoney) {
        this.agentMeMoney = agentMeMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}