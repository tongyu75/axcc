package com.axcc.utils;

import com.axcc.model.Voucher;
import com.axcc.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 定时任务
 */
@Component
public class ScheduledUtil {

    @Autowired
    VoucherService voucherService;

    //定义时间格式
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron="0 */5 * * * *")
    public void checkVoucher(){
        Voucher bean = new Voucher();
        bean.setVoucherStatus(1);
        List<Voucher> list = voucherService.listVoucherByBean(bean);
        if(list.size()>0){
            for(Voucher v : list){
                //获取到期时间
                String nowDate = sdf.format(new Date());
                String endDate = sdf.format(v.getVoucherFinish());
                int days = ToolsUtil.getDayFromTo(nowDate,endDate);
                if(days < 0){
                    Voucher voucher = new Voucher();
                    voucher.setId(v.getId());
                    voucher.setVoucherStatus(2); //优惠券未使用，已过期
                    voucher.setIsDelete(1); //优惠券过期，不能在使用
                    int value = voucherService.updateVoucherByBean(bean);
                    if(value != 1){
                        System.out.print("---------修改优惠券过期异常---------");
                    }
                }
            }
        }
    }
}
