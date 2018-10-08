package com.axcc.controller;

import com.axcc.model.Users;
import com.axcc.service.UserService;
import com.axcc.utils.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 GET：一般用于查询数据，不办函数据的更新以及插入操作。由于明文传输的关系，我们一般用来获取一些无关用户的信息。
 POST：一般用于数据的插入操作，也是使用最多的传输方式，但是在H5调用时会有跨域的问题，一般使用JSONP来解决。
 PUT：我们使用PUT方式来对数据进行更新操作。
 DELETE：用于数据删除，注意在数据库内是逻辑删除（改变数据状态，用户不再查询得到，但还保留在数据库内）还是物理删除（真删了）。
 * @author tongshiru
 *
 */
@RestController
public class UsersController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    /**
     * 用户登录
     * @param phone 手机号
     * @param pwd 密码
     */
    @RequestMapping(value="/login/{phone}/{pwd}",method = RequestMethod.GET)
    public Map<String,Object> login(@PathVariable String phone, @PathVariable String pwd){
        logger.info("login---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Users paramUsers = new Users();
        paramUsers.setLoginName(phone);
        paramUsers.setPassword(pwd);
        Users users = userService.selectUserByBean(paramUsers);
        if (users != null) {
            result.put("code", BaseResult.SUCCESS_CODE);
            result.put("msg", BaseResult.SUCCESS_MSG);
            result.put("result", users);
        } else {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
        }
        logger.info("login---end" + result.toString());
        return result;
    }

    /**
     * 会员注册
     * @param phone 手机号
     * @param userName 会员名称
     * @param parentId 推荐人手机号
     * @param pwd 密码
     */
    @RequestMapping(value="/user",method = RequestMethod.POST)
    public Map<String,Object> password(@RequestParam(value = "phone", required = true) String phone,
                                       @RequestParam(value = "userName", required = true) String userName,
                                       @RequestParam(value = "parentId", required = true) String parentId,
                                       @RequestParam(value = "pwd", required = true) String pwd){
        logger.info("user---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Users paramUsers = new Users();
        paramUsers.setLoginName(phone);
        paramUsers.setUserName(userName);
        paramUsers.setPassword(pwd);
        paramUsers.setParentId(parentId);
        Date date = new Date();
        paramUsers.setCreateTime(date);
        paramUsers.setUpdateTime(date);
        int value = userService.insertUserForBean(paramUsers);
        if (value == 1) {
            result.put("code", BaseResult.SUCCESS_CODE);
            result.put("msg", BaseResult.SUCCESS_MSG);
        } else {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
        }
        logger.info("user---end" + result.toString());
        return result;
    }

    /**
     * 重置密码
     * @param phone 手机号
     * @param pwd 密码
     */
    @RequestMapping(value="/resetPwd",method = RequestMethod.PUT)
    public Map<String,Object> resetPwd(@RequestParam(value = "phone", required = true) String phone, @RequestParam(value = "pwd", required = true) String pwd){
        logger.info("password---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Users paramUsers = new Users();
        paramUsers.setLoginName(phone);
        paramUsers.setPassword(pwd);
        paramUsers.setUpdateTime(new Date());
        int value = userService.resetPassword(paramUsers);
        if (value == 1) {
            result.put("code", BaseResult.SUCCESS_CODE);
            result.put("msg", BaseResult.SUCCESS_MSG);
        } else {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
        }
        logger.info("password---end" + result.toString());
        return result;
    }

    /**
     * 根据用户ID查询用户信息
     */
    @RequestMapping(value="/users/{id}",method = RequestMethod.GET)
    public Map<String,Object> users(@PathVariable int id){
        logger.info("users---start");
        Users users = userService.selectUserById(id);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("info", users);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        logger.info("users---end");
        return result;
    }
}
