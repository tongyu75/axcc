package com.axcc.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础的返回值
 */
public class BaseResult {
    /* 短信accessKeyId */
    public static final String ACCESSKEYID = "XXXXXXX";

    /* 短信accessKeySecret */
    public static final String ACCESSKEYSECRET = "XXXXXXXXX";

    /* 短信签名 */
    public static final String SIGNNAME = "亿宏顺达";

    /* 短信模板 */
    public static final String TEMPLATECODE = "您的验证码为：${code}，您正在注册成为平台会员，感谢您的支持！";

    /* 返回值CODE 0 成功*/
    public static final int SUCCESS_CODE = 0;

    /* 返回值MSG "SUCCESS" */
    public static final String SUCCESS_MSG = "SUCCESS";

    /* 返回值CODE 1 失败*/
    public static final int FAIL_CODE = 1;

    /* 返回值MSG 1 "FAIL" */
    public static final String FAIL_MSG = "FAIL";

    /* 判断操作的返回值 */
    public static Map<String,Object> checkResult(int value){
        Map<String,Object> map = new HashMap<String,Object>();
        if (value == 1) {
            map.put("code", SUCCESS_CODE);
            map.put("msg", SUCCESS_MSG);
        } else {
            map.put("code", FAIL_CODE);
            map.put("msg", FAIL_MSG);
        }
        return map;
    }

    /* 购车类型 buyType */
    public static final String BUYTYPE_1 = "五进一十万车型";
    public static final String BUYTYPE_2 = "五进二十万车型";
    public static final String BUYTYPE_3 = "五进三十万车型";
    public static final String BUYTYPE_5 = "五进五十万车型";

    /* 购车状态 buyStatus*/
    public static final String BUYSTATUS_0 = "未排队";
    public static final String BUYSTATUS_1 = "排队中";
    public static final String BUYSTATUS_2 = "已出车";

    /* 管理员审核状态 check_status*/
    public static final String CHECKSTATUS_0 = "已提交申请，未审核";
    public static final String CHECKSTATUS_1 = "审核通过，未缴费";
    public static final String CHECKSTATUS_2 = "审核通过，已缴费";
    public static final String CHECKSTATUS_3 = "审核通过，缴费失败";
    public static final String CHECKSTATUS_4 = "审核未通过";

    /* 用户登录CODE "用户不存在" */
    public static final int LOGIN_NO_USER = 1001;
    public static final String LOGIN_NO_USER_MSG = "用户不存在";

    /* 用户登录CODE "密码错误" */
    public static final int LOGIN_PWD_ERROR = 1002;
    public static final String LOGIN_PWD_ERROR_MSG = "密码错误";

    /* 管理员排号CODE "排号已存在" */
    public static final int WAIT_NUM_ERROR = 2;
    public static final String WAIT_NUM_ERROR_MSG = "排号已存在";
}
