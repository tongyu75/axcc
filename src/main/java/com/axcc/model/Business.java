package com.axcc.model;

import java.util.Date;

public class Business {
    private Integer id;

    private Integer userId;

    private Integer agentId;

    private Integer adminId;

    private Integer waitNum;

    private Float buyMoney;

    private Integer buyType;

    private Integer buyStatus;

    private Integer checkStatus;

    private Date checkTime;

    private Date payTime;

    private Date applyTime;

    private Integer moneyApplyStatus;

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

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getWaitNum() {
        return waitNum;
    }

    public void setWaitNum(Integer waitNum) {
        this.waitNum = waitNum;
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

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getMoneyApplyStatus() {
        return moneyApplyStatus;
    }

    public void setMoneyApplyStatus(Integer moneyApplyStatus) {
        this.moneyApplyStatus = moneyApplyStatus;
    }
}