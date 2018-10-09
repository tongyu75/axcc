package com.axcc.dao;

import com.axcc.model.Voucher;

public interface VoucherDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Voucher record);

    int insertSelective(Voucher record);

    Voucher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Voucher record);

    int updateByPrimaryKey(Voucher record);
}