package com.axcc.controller;

import com.axcc.model.Business;
import com.axcc.model.BusinessUser;
import com.axcc.model.Users;
import com.axcc.service.BusinessService;
import com.axcc.service.UserService;
import com.axcc.utils.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    BusinessService businessService;
    /**
     * 用户登录
     * @param phone 手机号
     * @param pwd 密码
     */
    @RequestMapping(value="/login/{phone}/{pwd}",method = RequestMethod.GET)
    public Map<String,Object> login(HttpServletRequest reuest, @PathVariable String phone, @PathVariable String pwd){
        logger.info("login---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Users paramUsers = new Users();
        paramUsers.setLoginName(phone);
        paramUsers.setPassword(pwd);
        Users users = userService.getUserByBean(paramUsers);
        if (users != null) {
            // 存到session中
            reuest.getSession().setAttribute("user", users);
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
        // 会员角色
        paramUsers.setUserRole(2);
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
    @RequestMapping(value="/resetPwd",method = RequestMethod.POST)
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
     * 代理员添加
     * @param proxyArea 代理区域
     * @param userName 代理名
     * @param phone 手机号
     */
    @RequestMapping(value="/proxy",method = RequestMethod.POST)
    public Map<String,Object> proxy(@RequestParam(value = "proxyArea") String proxyArea,
                                       @RequestParam(value = "userName") String userName,
                                       @RequestParam(value = "phone") String phone){
        logger.info("user---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Users paramUsers = new Users();
        paramUsers.setLoginName(phone);
        paramUsers.setUserName(userName);
        paramUsers.setProxyArea(proxyArea);
        paramUsers.setPassword("123456");
        // 代理员角色
        paramUsers.setUserRole(1);
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
     * 代理员详情
     * @param id 代理员ID
     */
    @RequestMapping(value="/proxy/{id}",method = RequestMethod.GET)
    public Map<String,Object> proxyDetail(@PathVariable int id){
        logger.info("proxyDetail---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Users user = userService.getUserById(id);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info", user);
        logger.info("proxyDetail---end" + result.toString());
        return result;
    }

    /**
     * 代理员删除
     * @param id 代理员ID
     */
    @RequestMapping(value="/proxy/{id}",method = RequestMethod.DELETE)
    public Map<String,Object> deleteProxy(@PathVariable int id){
        logger.info("proxyDelete---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Users user = new Users();
        user.setId(id);
        // 逻辑删除
        user.setIsDelete(1);
        int value = userService.updateUserForBean(user);
        if (value == 1) {
            result.put("code", BaseResult.SUCCESS_CODE);
            result.put("msg", BaseResult.SUCCESS_MSG);
        } else {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
        }
        logger.info("proxyDelete---end" + result.toString());
        return result;
    }

    /**
     * 代理员列表
     * @param pageNum 第几页
     * @param pageSize 每页显示的个数
     */
    @RequestMapping(value="/proxy/{pageNum}/{pageSize}",method = RequestMethod.GET)
    public Map<String,Object> listProxy(@PathVariable int pageNum,@PathVariable int pageSize){
        logger.info("listProxy---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Users user = new Users();
        // 查询记录总条数
        int count = userService.countUserByBean(user);
        // 查询记录
        List<Users> lstUser = userService.listUserByBean(user, pageNum, pageSize);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info", lstUser);
        result.put("total", count);
        logger.info("listProxy---end" + result.toString());
        return result;
    }
    /**
     * 按购车类型获取会员申请列表
     */
    @RequestMapping(value="/listBuyTypeUser",method = RequestMethod.POST)
    public Map<String,Object> listBuyTypeUser(@RequestParam(value="pageNum",required = true) int pageNum,
                                              @RequestParam(value="pageSize",required = true) int pageSize,
                                              @RequestParam(value="buyType",required = true) int buyType){
        logger.info("listBuyTypeUser---------start------");
        //返回值
        Map<String,Object> result = new HashMap<String,Object>();
        //查询条件，buyType:购车类型，1:10万车型，2:20万车型；3:30万车型；5:50万车型
        BusinessUser bean = new BusinessUser();
        bean.setBuyType(buyType);
        //记录查询总数
        int count = businessService.countBusinessUserByBean(bean);
        //查询记录
        List<BusinessUser> lstBusinessUser = businessService.listBusinessUserByBean(bean,pageNum,pageSize);
        result.put("code",BaseResult.SUCCESS_CODE);
        result.put("msg",BaseResult.SUCCESS_MSG);
        result.put("info",lstBusinessUser);
        result.put("total",count);
        logger.info("listBuyTypeUser--------end-------");
        return result;
    }
    /**
     * 获取会员列表，可以按登录名条件查询
     *
     */
    @RequestMapping(value="/memberList",method = RequestMethod.POST)
    public Map<String,Object> memberList(@RequestParam(value="pageNum") int pageNum,
                                         @RequestParam(value="pageSize") int pageSize,
                                         @RequestParam(value="loginName") String loginName){
        logger.info("listmember");
        //返回值
        Map<String,Object> result = new HashMap<String,Object>();
        //查询条件
        Users user = new Users();
        if(!"".equals(loginName) && null != loginName){
            user.setLoginName(loginName);
        }
        user.setUserRole(2);
        //查询记录总数
        int count = userService.countUserByBean(user);
        //查询记录
        List<Users> listUser = userService.listUserByBean(user,pageNum,pageSize);
        result.put("code",BaseResult.SUCCESS_CODE);
        result.put("msg",BaseResult.SUCCESS_MSG);
        result.put("info",listUser);
        result.put("total",count);
        logger.info("listMember---end"+result.toString());
        return result;
    }

    /**
     * 会员详情
     * @param id 会员ID
     */
    @RequestMapping(value="/member/{id}",method = RequestMethod.GET)
    public Map<String,Object> memberDetail(@PathVariable int id){
        logger.info("member---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Users user = userService.getUserById(id);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info", user);
        logger.info("member---end" + result.toString());
        return result;
    }

    /**
     * 会员删除
     * @param id 会员ID
     */
    @RequestMapping(value="/member/{id}",method = RequestMethod.DELETE)
    public Map<String,Object> memberDelete(@PathVariable int id){
        logger.info("member---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Users user = new Users();
        user.setId(id);
        // 逻辑删除
        user.setIsDelete(1);
        int value = userService.updateUserForBean(user);
        if (value == 1) {
            result.put("code", BaseResult.SUCCESS_CODE);
            result.put("msg", BaseResult.SUCCESS_MSG);
        } else {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
        }
        logger.info("member---end" + result.toString());
        return result;
    }

    /**
     * 根据用户ID查询用户信息
     */
    @RequestMapping(value="/users/{id}",method = RequestMethod.GET)
    public Map<String,Object> users(@PathVariable int id){
        logger.info("users---start");
        Users users = userService.getUserById(id);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("info", users);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        logger.info("users---end");
        return result;
    }

    /**
     * 会员登录：申请排队
     */
    @RequestMapping(value="/memberQueue",method = RequestMethod.POST)
    public Map<String,Object> memberQueue(@RequestParam(value = "userId", required = true) Integer userId,
                                          @RequestParam(value = "buyType", required = true) Integer buyType){
        logger.info("memberQueue---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Business business = new Business();
        business.setUserId(userId);
        // 购车类型（1：10万，2:20万，3:30万，5:50万）
        business.setBuyType(buyType);
        // 管理员审核状态（0：会员已提交申请，未审核；1：审核通过，未缴费；2：审核通过，已缴费；3：审核通过，缴费失败；4：审核未通过）
        business.setCheckStatus(0);
        // 申请时间
        business.setApplyTime(new Date());
        int value = businessService.insertBusinessForBean(business);
        if (value == 1) {
            result.put("code", BaseResult.SUCCESS_CODE);
            result.put("msg", BaseResult.SUCCESS_MSG);
        } else {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
        }
        logger.info("memberQueue---end");
        return result;
    }

    /**
     * 管理员审核状态和代理员审核状态的更新
     */
    @RequestMapping(value="/updateCheckStatus",method = RequestMethod.POST)
    public Map<String,Object> updateCheckStatus(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "checkStatus", required = true) Integer checkStatus){
        logger.info("updateCheckStatus---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Business business = new Business();
        business.setId(id);
        // 管理员审核状态（0：会员已提交申请，未审核；1：审核通过，未缴费；2：审核通过，已缴费；3：审核通过，缴费失败；4：审核未通过）
        business.setCheckStatus(checkStatus);
        // 审核时间
        business.setCheckTime(new Date());
        int value = businessService.updateBusinessForBean(business);
        if (value == 1) {
            result.put("code", BaseResult.SUCCESS_CODE);
            result.put("msg", BaseResult.SUCCESS_MSG);
        } else {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
        }
        logger.info("updateCheckStatus---end");
        return result;
    }

    /**
     * 管理员登陆查看会员的缴费详情和排队详情以及代理员登陆查看详情
     */
    @RequestMapping(value="/businessDetail",method = RequestMethod.POST)
    public Map<String,Object> businessDetail(
            @RequestParam(value = "id", required = true) Integer id){
        logger.info("business---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        BusinessUser business = businessService.getBusinessUserById(id);
        if (business != null) {
            result.put("code", BaseResult.SUCCESS_CODE);
            result.put("msg", BaseResult.SUCCESS_MSG);
            result.put("result", business);
        } else {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
        }
        logger.info("business---end");
        return result;
    }

    /**
     * 管理员登录：为会员购车排号
     */
    @RequestMapping(value="/updateWaitNum",method = RequestMethod.POST)
    public Map<String,Object> updateQueueNum(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "waitNum", required = true) Integer waitNum){
        logger.info("updateQueueNum---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Business business = new Business();
        business.setId(id);
        // 排位号
        business.setWaitNum(waitNum);
        int value = businessService.updateBusinessForBean(business);
        if (value == 1) {
            result.put("code", BaseResult.SUCCESS_CODE);
            result.put("msg", BaseResult.SUCCESS_MSG);
        } else {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
        }
        logger.info("updateQueueNum---end");
        return result;
    }

    /**
     * 排队列表
     * @param pageNum 第几页
     * @param pageSize 每页显示的个数
     */
    @RequestMapping(value="/listWait",method = RequestMethod.POST)
    public Map<String,Object> listWait(
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "pageNum", required = true) Integer pageNum,
            @RequestParam(value = "pageSize", required = true) Integer pageSize){
        logger.info("listWait---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        BusinessUser bean = new BusinessUser();
        bean.setLoginName(phone);
        // 查询记录总条数
        int count = businessService.countBusinessUserByBean(bean);
        // 查询记录
        List<BusinessUser> lstBusinessUser = businessService.listBusinessUserByBean(bean, pageNum, pageSize);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info", lstBusinessUser);
        result.put("total", count);
        logger.info("listWait---end" + result.toString());
        return result;
    }

    /**
     * 代理员登录：输入的实缴金额
     */
    @RequestMapping(value="/updateBuyMoney",method = RequestMethod.POST)
    public Map<String,Object> updateBuyMoney(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "buyMoney", required = true) Float buyMoney){
        logger.info("updateCheckStatus---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Business business = new Business();
        business.setId(id);
        // 实缴金额
        business.setBuyMoney(buyMoney);
        // 管理员审核状态（0：会员已提交申请，未审核；1：审核通过，未缴费；2：审核通过，已缴费；3：审核通过，缴费失败；4：审核未通过）
        business.setCheckStatus(2);
        // 会员购车状态（0：未排队，1：排队中，2：已出车）
        business.setBuyStatus(2);
        int value = businessService.updateBusinessForBean(business);
        if (value == 1) {
            result.put("code", BaseResult.SUCCESS_CODE);
            result.put("msg", BaseResult.SUCCESS_MSG);
        } else {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
        }
        logger.info("updateCheckStatus---end");
        return result;
    }
}
