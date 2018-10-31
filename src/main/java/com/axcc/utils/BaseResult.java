package com.axcc.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础的返回值
 */
public class BaseResult {

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
}
