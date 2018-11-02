package com.axcc.controller;

import com.axcc.model.Voucher;
import com.axcc.service.BusinessService;
import com.axcc.service.UserService;
import com.axcc.service.VoucherService;
import com.axcc.utils.BaseResult;
import com.axcc.utils.ToolsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
    @RequestMapping(value = "getUseVoucherInfo",method = RequestMethod.POST)
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
        //获取到期时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = sdf.format(new Date());
        String endDate = sdf.format(vou.getVoucherFinish());
        int days = ToolsUtil.getDayFromTo(nowDate,endDate);
        if(days < 0){
            Voucher bean = new Voucher();
            bean.setId(vou.getId());
            bean.setVoucherStatus(2); //优惠券未使用，已过期
            bean.setIsDelete(1); //优惠券过期，不能在使用
            int value = voucherService.updateVoucherByBean(bean);
            if(value != 1){
                result.put("msg", BaseResult.FAIL_MSG);
                result.put("code", BaseResult.FAIL_CODE);
                result.put("info",null);
                return result;
            }
        }
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info",vou);
        logger.info("getVoucherByBean-----------end");
        return result;
    }

    /**
     * 根据用户ID查询优惠券，查看个人优惠券信息时使用本方法
     *
     * */
    @RequestMapping(value = "getVoucherByUserId",method = RequestMethod.POST)
    public Map<String,Object> getVoucherByUserId(@RequestParam(value = "userId") int userId){
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
        int voucherId = vou.getId(); //获取优惠券ID
        String userName = userService.getUserById(userId).getUserName(); //获取会员名称
        //获取到期时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = sdf.format(new Date());
        String endDate = sdf.format(vou.getVoucherFinish());
        int days = ToolsUtil.getDayFromTo(nowDate,endDate);
        if(days < 0){
            Voucher bean = new Voucher();
            bean.setId(voucherId);
            bean.setVoucherStatus(2); //优惠券未使用，已过期
            bean.setIsDelete(1); //优惠券过期，不能在使用
            int value = voucherService.updateVoucherByBean(bean);
            if(value != 1){
                result.put("msg", BaseResult.FAIL_MSG);
                result.put("code", BaseResult.FAIL_CODE);
                return result;
            }
        }
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("userName",userName);
        result.put("days",days);
        result.put("info",vou);

        logger.info("getVoucherByBean-----------end");
        return result;
    }
}
