package com.axcc.dao;

import com.axcc.model.Voucher;

import java.util.List;

public interface VoucherDao {

    Voucher getVoucherById(Integer id);

    Voucher getVoucherByBean(Voucher bean);

    List<Voucher> listVoucherByBean(Voucher bean);

    int countVoucherByBean(Voucher bean);

    int insertVoucherByBean(Voucher bean);

    int updateVoucherByBean(Voucher bean);

    //----------------------------------------------------
    
    int deleteByPrimaryKey(Integer id);

    int insert(Voucher record);

    int insertSelective(Voucher record);

    Voucher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Voucher record);

    int updateByPrimaryKey(Voucher record);
}