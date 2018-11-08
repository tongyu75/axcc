package com.axcc.service.impl;

import com.axcc.dao.VoucherDao;
import com.axcc.model.Voucher;
import com.axcc.service.VoucherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value="voucherService")
public class VoucherServiceImpl implements VoucherService {

    @Resource
    private VoucherDao voucherDao;

    public int insertVoucherByBean(Voucher bean){
        return voucherDao.insertVoucherByBean(bean);
    }

    public Voucher getVoucherByBean(Voucher bean){
        return voucherDao.getVoucherByBean(bean);
    }

    public List<Voucher> listVoucherByBean(Voucher bean){
        return voucherDao.listVoucherByBean(bean);
    }

    public int updateVoucherByBean(Voucher bean){
        return voucherDao.updateVoucherByBean(bean);
    }

    public int deleteVoucherByUserId(Integer userId){
        return voucherDao.deleteVoucherByUserId(userId);
    }
}
