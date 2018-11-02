package com.axcc.model;

import java.util.Date;

public class Voucher {
    private Integer id;

    private Integer userId;

    private Float voucherMoney;

    private Integer voucherStatus;

    private Date voucherTime;

    private Date voucherFinish;

    private String description;

    private Integer isDelete;

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

    public Float getVoucherMoney() {
        return voucherMoney;
    }

    public void setVoucherMoney(Float voucherMoney) {
        this.voucherMoney = voucherMoney;
    }

    public Integer getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(Integer voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    public Date getVoucherTime() {
        return voucherTime;
    }

    public void setVoucherTime(Date voucherTime) {
        this.voucherTime = voucherTime;
    }

    public Date getVoucherFinish() {
        return voucherFinish;
    }

    public void setVoucherFinish(Date voucherFinish) {
        this.voucherFinish = voucherFinish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }


    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

}