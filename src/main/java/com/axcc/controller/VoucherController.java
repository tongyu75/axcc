package com.axcc.controller;

import com.axcc.model.Voucher;
import com.axcc.service.BusinessService;
import com.axcc.service.UserService;
import com.axcc.service.VoucherService;
import com.axcc.utils.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class VoucherController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    VoucherService voucherService;

    @Autowired
    BusinessService businessService;

    @Autowired
    UserService userService;

    /**
     * 根据用户ID查询用户可用的优惠券，缴费时使用本方法
     */
    @RequestMapping(value = "/getUseVoucherInfo",method = RequestMethod.POST)
    public Map<String,Object> getUseVoucherInfo(@RequestParam(value = "userId") int userId) {
        logger.info("getVoucherByBean-----------start");
        //返回类型
        Map<String,Object> result = new HashMap<String,Object>();
        //获取优惠券
        Voucher voucher = new Voucher();
        voucher.setUserId(userId);
        voucher.setIsDelete(0);
        voucher.setVoucherStatus(1);
        Voucher vou = voucherService.getVoucherByBean(voucher);
        if(null == vou){
            result.put("msg", BaseResult.SUCCESS_MSG);
            result.put("code", BaseResult.SUCCESS_CODE);
            result.put("info",null);
            return result;
        }
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info",vou);
        logger.info("getVoucherByBean-----------end");
        return result;
    }

    /**
     * 根据用户ID查询优惠券列表
     *
     * */
    @RequestMapping(value = "/getListVoucherByUserId",method = RequestMethod.POST)
    public Map<String,Object> getVoucherByUserId(@RequestParam(value = "userId") int userId){
        logger.info("getListVoucherByUserId-----------start");

        //返回类型
        Map<String,Object> result = new HashMap<String,Object>();
        //获取优惠券
        Voucher voucher = new Voucher();
        voucher.setUserId(userId);
        voucher.setVoucherStatus(1); //未使用且未过期
        voucher.setIsDelete(0); //未删除
        List<Voucher> vou = voucherService.listVoucherByBean(voucher);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("info",vou);

        logger.info("getListVoucherByUserId-----------end");
        return result;
    }
}
