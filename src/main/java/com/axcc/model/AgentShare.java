package com.axcc.model;

import java.util.Date;

public class AgentShare {
    private Integer id;

    private Integer userId;

    private Integer parentId;

    private Float agentMoney;

    private Date createDate;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
}