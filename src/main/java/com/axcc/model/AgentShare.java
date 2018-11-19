package com.axcc.model;

import java.util.Date;

public class AgentShare {
    private Integer id;

    private Integer agentId;

    private Integer userId;

    private Integer businessId;

    private Float agentMoney;

    private Date createDate;

    private Integer applyStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBusinesstId() { return businessId; }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Float getAgentMoney() {
        return agentMoney;
    }

    public void setAgentMoney(Float agentMoney) {
        this.agentMoney = agentMoney;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }
}