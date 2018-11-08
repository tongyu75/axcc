package com.axcc.service;

import com.axcc.model.Voucher;

import java.util.List;

public interface VoucherService {

    int insertVoucherByBean(Voucher voucher);

    Voucher getVoucherByBean(Voucher bean);

    List<Voucher> listVoucherByBean(Voucher bean);

    int updateVoucherByBean(Voucher bean);

    int deleteVoucherByUserId(Integer userId);
}
