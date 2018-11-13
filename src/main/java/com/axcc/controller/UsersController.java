package com.axcc.controller;

import com.axcc.model.*;
import com.axcc.service.*;
import com.axcc.utils.BaseResult;
import com.axcc.utils.FileResourcePathUtil;
import com.axcc.utils.QRCodeConstants;
import com.axcc.utils.QRCodeUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    private final ResourceLoader resourceLoader;

    @Autowired
    public UsersController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Autowired
    UserService userService;

    @Autowired
    BusinessService businessService;

    @Autowired
    VoucherService voucherService;

    @Autowired
    MoneyApplyService moneyApplyService;

    @Autowired
    MoneyApplyDetailService moneyApplyDetailService;

    @Autowired
    UserRelateService userRelateService;

    @Autowired
    AgentShareService agentShareService;

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

        // 根据parentId获取推荐人的original，用来生成注册人的original字段
        Users paramRef = new Users();
        paramRef.setLoginName(parentId);
        Users refUses = userService.getUserByBean(paramRef);
        // refOriginal的值如:32-31
        String refOriginal = refUses.getOriginal();

        // 插入注册会员
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
            // 更新注册会员的original
            int addId = paramUsers.getId();
            // original的值如：32-32-30
            String original = refOriginal + "-" + addId;
            Users updateBean = new Users();
            updateBean.setId(addId);
            updateBean.setOriginal(original);
            int updateValue = userService.updateUserForBean(updateBean);
            if (updateValue == 1) {
                result.put("code", BaseResult.SUCCESS_CODE);
                result.put("msg", BaseResult.SUCCESS_MSG);
            } else {
                result.put("code", BaseResult.FAIL_CODE);
                result.put("msg", BaseResult.FAIL_MSG);
            }
        } else {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
        }
        logger.info("user---end" + result.toString());
        return result;
    }

    /**
     * 根据手机号查看用户信息
     */
    @RequestMapping(value = "getUserByLoginName",method = RequestMethod.POST)
    public Map<String,Object> getUserByLoginName(@RequestParam(value = "loginName",required = true) String loginName){
        logger.info("getUserByLoginName---------------start");
        //返回类型
        Map<String,Object> result = new HashMap<String,Object>();
        Users user = userService.getUserByLoginName(loginName);
        result.put("msg",BaseResult.SUCCESS_MSG);
        result.put("code",BaseResult.SUCCESS_CODE);
        result.put("info",user);
        logger.info("getUserByLoginName---------------end");
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
     * 修改个人信息
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public Map<String, Object> updateUserInfo(@RequestParam(value = "id") int id,
                                              @RequestParam(value = "userName") String userName,
                                              @RequestParam(value = "realName") String realName,
                                              @RequestParam(value = "sex") int sex,
                                              @RequestParam(value = "card") String card,
                                              @RequestParam(value = "bankCard") String bankCard,
                                              @RequestParam(value = "bankAddr") String bankAddr) {
        logger.info("updateUserInfo-------start");
        //返回值
        Map<String, Object> result = new HashMap<String, Object>();
        Users paramUsers = new Users();
        paramUsers.setId(id);
        paramUsers.setUserName(userName);
        paramUsers.setRealName(realName);
        paramUsers.setSex(sex);
        paramUsers.setCard(card);
        paramUsers.setBankCard(bankCard);
        paramUsers.setBankAddr(bankAddr);
        int value = userService.updateUserForBean(paramUsers);
        if (value == 1) {
            result.put("code", BaseResult.SUCCESS_CODE);
            result.put("msg", BaseResult.SUCCESS_MSG);
        } else {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
        }
        logger.info("updateUserInfo-----" + value + "----end" + result.toString());
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
        int value = 0;
        Users paramUsers = new Users();
        paramUsers = userService.getUserByLoginName(phone);
        //如果该代理员已经是会员了，直接更改角色为代理员
        if(null != paramUsers){
            paramUsers.setUserRole(1);
            paramUsers.setUserName(userName);
            paramUsers.setProxyArea(proxyArea);
            Date date = new Date();
            paramUsers.setCreateTime(date);
            paramUsers.setUpdateTime(date);
            value = userService.updateUserForBean(paramUsers);
        }else{
            //如果该代理员还不是会员，则添加为代理员,默认密码为123456
            Users bean1 = new Users();
            bean1.setLoginName(phone);
            bean1.setUserName(userName);
            bean1.setProxyArea(proxyArea);
            bean1.setPassword("123456");
            // 代理员角色
            bean1.setUserRole(1);
            Date date = new Date();
            bean1.setCreateTime(date);
            bean1.setUpdateTime(date);
            value = userService.insertUserForBean(bean1);
        }
        result = BaseResult.checkResult(value);
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
        user.setUserRole(1);
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
     * 会员登录：按购车类型获取会员申请列表
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
     * 会员登录：返回排号信息
     *
     * */
    @RequestMapping(value="/getMemberInfo",method = RequestMethod.POST)
    public Map<String,Object> getMemberInfo(@RequestParam(value = "userId", required = true) Integer userId){
        logger.info("getMemberInfo---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        BusinessUser bUser = businessService.getBusinessUsersByUserId(userId); //根据用户ID获取用户及排队信息
        int total = businessService.getBuyStatusCount(); //获取已提车总量
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("buyTotal",total);
        result.put("info",bUser);
        logger.info("getMemberInfo---end");
        return result;
    }
    /**
     * 获取会员列表，可以按登录名条件查询
     *
     */
    @RequestMapping(value="/memberList",method = RequestMethod.POST)
    public Map<String,Object> memberList(@RequestParam(value="pageNum",required = true) int pageNum,
                                         @RequestParam(value="pageSize",required = true) int pageSize,
                                         @RequestParam(value="loginName",required = false) String loginName){
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
     * 根据ID获取会员详情
     * @param id 会员ID
     */
    @RequestMapping(value="/member/{id}",method = RequestMethod.GET)
    public Map<String,Object> memberDetail(@PathVariable(value = "id",required  = true ) int id){
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
     * 根据手机号获取会员详情
     * @param loginName 会员手机号
     */
    @RequestMapping(value="/memberByLoginName",method = RequestMethod.POST)
    public Map<String,Object> memberByLoginName(@RequestParam(value = "loginName",required = true) String loginName){
        logger.info("memberByLoginName---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Users user = userService.getUserByLoginName(loginName);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info", user);
        logger.info("memberByLoginName---end" + result.toString());
        return result;
    }

    /**
     * 根据用户手机号删除
     */
    @RequestMapping(value = "delUserByLoginName",method = RequestMethod.DELETE)
    public Map<String,Object> delUserByLoginName(
            @RequestParam(value = "loginName", required = true) String loginName){
        //返回类型
        Map<String,Object> map = new HashMap<String,Object>();
        Users user = userService.getUserByLoginName(loginName);
        if(null != user){
            map = memberDelete(user.getId());
        }else{
            map.put("code", BaseResult.FAIL_CODE);
            map.put("msg", BaseResult.FAIL_MSG);
        }
        return map;
    }

    /**
     * 根据ID删除会员
     * @param id 会员ID
     */
    @RequestMapping(value="/member/{id}",method = RequestMethod.DELETE)
    public Map<String,Object> memberDelete(@PathVariable int id){
        logger.info("member---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        //删除优惠券信息(逻辑删除)
        Voucher voucher = new Voucher();
        voucher.setUserId(id);
        List<Voucher> listVou = voucherService.listVoucherByBean(voucher);
        if(listVou.size() > 0){
            int delVou = voucherService.deleteVoucherByUserId(id);
            if (delVou != 1) {
                result.put("code", BaseResult.FAIL_CODE);
                result.put("msg", BaseResult.FAIL_MSG);
                return result;
            }
        }
        //删除业务信息(逻辑删除)
        Business business = new Business();
        business.setUserId(id);
        int count = businessService.countBusinessByBean(business);
        if(count > 0){
            int delBusi = businessService.deleteByUserId(id);
            if (delBusi != 1) {
                result.put("code", BaseResult.FAIL_CODE);
                result.put("msg", BaseResult.FAIL_MSG);
                return result;
            }
        }
        //删除用户信息
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
     * 会员登录：申请排队购车
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
        //购车状态，0 未排队，1 排队中，2 已出车
        business.setBuyStatus(0);
        // 管理员审核状态（0：会员已提交申请，未审核；1：审核通过，未缴费；2：审核通过，已缴费；3：审核通过，缴费失败；4：审核未通过）
        business.setCheckStatus(0);
        //业务状态：0 未删除，1 已删除
        business.setIsDelete(0);
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
     * 管理员登录：查看所有用户信息
     */
    @RequestMapping(value = "getAllUserList",method = RequestMethod.POST)
    public Map<String,Object> getAllUserList(
        @RequestParam(value = "phone", required = false) String phone,
        @RequestParam(value = "pageNum", required = true) Integer pageNum,
        @RequestParam(value = "pageSize", required = true) Integer pageSize){
            logger.info("getAllUserList---start");
            // 返回值
            Map<String,Object> result = new HashMap<String, Object>();
            BusinessUser bean = new BusinessUser();
            if(!"".equals(phone)){
                bean.setLoginName(phone);
            }

            // 查询记录总条数
            int count = businessService.countAllUserList(bean);
            // 查询记录
            List<Map<String,Object>> lstBusinessUser = businessService.listAllUserByBean(bean, pageNum, pageSize);
            result.put("code", BaseResult.SUCCESS_CODE);
            result.put("msg", BaseResult.SUCCESS_MSG);
            result.put("info", lstBusinessUser);
            result.put("total", count);
            logger.info("getAllUserList---end" + result.toString());
            return result;
    }

    /**
     * 管理员审核状态和代理员审核状态的更新
     * 参数：id  业务对应的ID
     *      checkStatus  审核状态
     *      checkStatus   是否使用优惠券，只有交钱时才才传值
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
    public Map<String,Object> businessDetail(@RequestParam(value = "id") Integer id){
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
        business.setBuyStatus(1);
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
        if(!"".equals(phone)){
            bean.setLoginName(phone);
        }
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
     * 代理员：代办业务列表,即没付费的用户信息
     */
    @RequestMapping(value = "listBusinessNoMoney",method = RequestMethod.POST)
    public Map<String,Object> listBusinessNoMoney(
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "pageNum", required = true) Integer pageNum,
            @RequestParam(value = "pageSize", required = true) Integer pageSize){
        logger.info("listWait---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        BusinessUser bean = new BusinessUser();
        if(!"".equals(phone)) {
            bean.setLoginName(phone);
        }
        bean.setCheckStatus(1);
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
     * 代理员：由登录的代理员办理的业务列表
     */
    @RequestMapping(value = "listBusinessByAgent",method = RequestMethod.POST)
    public Map<String,Object> listBusinessByAgent(HttpServletRequest request,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "pageNum", required = true) Integer pageNum,
            @RequestParam(value = "pageSize", required = true) Integer pageSize){
        logger.info("listWait---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        //从session中获取当前登录的用户信息
        Users user = (Users)request.getSession().getAttribute("user");
        BusinessUser bean = new BusinessUser();
        if(!"".equals(phone)) {
            bean.setLoginName(phone);
        }
        bean.setAgentId(user.getId());
        // 查询记录总条数
        int count = businessService.countBusinessByAgent(bean);
        // 查询记录
        List<BusinessUser> lstBusinessUser = businessService.listBusinessByAgent(bean, pageNum, pageSize);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info", lstBusinessUser);
        result.put("total", count);
        logger.info("listWait---end" + result.toString());
        return result;
    }

    /**
     * 代理员登录：输入的实缴金额
     * 参数：id  业务对应的ID
     *      buyMoney   排队购车所需费用
     *      agentId    登录的代理员ID
     *      voucherId   优惠券ID
     *      voucherUsed  是否使用优惠券 0：未使用，1 使用
     */
    @RequestMapping(value="/updateBuyMoney",method = RequestMethod.POST)
    public Map<String,Object> updateBuyMoney(HttpServletRequest request,
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "buyMoney", required = true) Float buyMoney,
            @RequestParam(value = "agentId", required = true) Integer agentId,
            @RequestParam(value = "voucherId", required = false) Integer voucherId,
            @RequestParam(value = "voucherUsed", required = true) Integer voucherUsed){
        logger.info("updateCheckStatus---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        //从session中获取当前登录的用户信息
    //    Users user = (Users)request.getSession().getAttribute("user");
        Users user = userService.getUserById(agentId);
        //使用使用优惠券
        if(1 == voucherUsed){
            //更新使用后优惠券信息
            Voucher bean = new Voucher();
            bean.setId(voucherId);
            bean.setVoucherStatus(3);
            bean.setIsDelete(1);
            int value = voucherService.updateVoucherByBean(bean);
            if(value != 1){
                result.put("msg", BaseResult.FAIL_MSG);
                result.put("code", BaseResult.FAIL_CODE);
                return result;
            }
        }

        //会员付费
        Business business = new Business();
        business.setId(id);
        business.setAgentId(user.getId());
        business.setBuyMoney(buyMoney);// 实缴金额
        //管理员审核状态（0：会员已提交申请，未审核；1：审核通过，未缴费；2：审核通过，已缴费；3：审核通过，缴费失败；4：审核未通过）
        business.setCheckStatus(2);
        business.setBuyStatus(0);// 会员购车状态（0：未排队，1：排队中，2：已出车）
        business.setPayTime(new Date());
        int value = businessService.updateBusinessForBean(business);
        if (value == 1) {
            if(0 == voucherUsed){
                result = insertVoucherInfo(id); //未使用优惠券缴费，为用户发放优惠券
            }else{
                result.put("code", BaseResult.SUCCESS_CODE);
                result.put("msg", BaseResult.SUCCESS_MSG);
            }

            // 插入代理员业绩表
            Business bean = businessService.getBusinessById(id);
            int userId = bean.getUserId();
            AgentShare agentShare = new AgentShare();
            agentShare.setAgentId(agentId);
            agentShare.setUserId(userId);
            agentShare.setBusinessId(id);
            agentShare.setAgentMoney(buyMoney);
            agentShare.setCreateDate(new Date());
            int val = agentShareService.insertAgentShareForBean(agentShare);
            if (val == 1) {
                result.put("code", BaseResult.SUCCESS_CODE);
                result.put("msg", BaseResult.SUCCESS_MSG);
            } else {
                result.put("code", BaseResult.FAIL_CODE);
                result.put("msg", BaseResult.FAIL_MSG);
            }

            // 插入新的数据到会员所属关系表(users_relate)
            // 获取用户信息
            Users users = userService.getUserById(userId);
            String[] ori = users.getOriginal().split("-");
            // "ori.length - 1"这里减1是因为插入的数据只有当前用户之前的数据而不包括自己的
            for(int i = 0; i < ori.length - 1; i++) {
                String parentId = ori[i];
                UsersRelate usersRelate = new UsersRelate();
                usersRelate.setUserId(Integer.valueOf(parentId));
                usersRelate.setOriginal(parentId);
                // 提现状态(0:未提现 1:已提现)
                usersRelate.setApplyStatus(0);
                // 购车费用
                usersRelate.setBuyMoney(bean.getBuyMoney());
                // 管理员审核时间
                usersRelate.setApplyTime(bean.getApplyTime());
                // 会员申请时间
                usersRelate.setCheckTime(bean.getCheckTime());
                // 会员级别
                usersRelate.setLevel(ori.length - 1 - i);
                // 当前会员对应的子会员的用户ID
                usersRelate.setChildId(bean.getUserId());
                int val1 = userRelateService.insertUserRelateForBean(usersRelate);
                if (val1 == 1) {
                    result.put("code", BaseResult.SUCCESS_CODE);
                    result.put("msg", BaseResult.SUCCESS_MSG);
                } else {
                    result.put("code", BaseResult.FAIL_CODE);
                    result.put("msg", BaseResult.FAIL_MSG);
                }
            }
        } else {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
        }
        logger.info("updateCheckStatus---end");
        return result;
    }

    /**
     * 代理员业绩表
     */
    @RequestMapping(value="/listAgentShare",method = RequestMethod.POST)
    public Map<String,Object> listAgentShare(
            @RequestParam(value = "agentId", required = true) Integer agentId,
            @RequestParam(value = "pageNum", required = true) Integer pageNum,
            @RequestParam(value = "pageSize", required = true) Integer pageSize){
        logger.info("listShareMoney---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        int count = agentShareService.countAgentShareById(agentId);
        Map<String,Object> lstMap = agentShareService.listAgentShareById(agentId, pageNum, pageSize);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info", lstMap);
        result.put("total", count);
        logger.info("listShareMoney---end" + result.toString());
        return result;
    }

    /**
     * 代理员提现申请
     * @param agentId 代理员ID
     * @param userStatus 用户类型
     */
    @RequestMapping(value="/agentWithdrawCashes",method = RequestMethod.POST)
    public Map<String,Object> agentWithdrawCashes(@RequestParam(value = "agentId", required = true) Integer agentId,
                                                  @RequestParam(value = "userStatus", required = true) Integer userStatus){
        logger.info("agentWithdrawCashes---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        // 首先判断提现的时间是否是每月的5号，如果不是就不能继续提现操作
        Calendar cal= Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 5) {
            // 判断是否重复提交
            MoneyApply bean = new MoneyApply();
            bean.setUserId(agentId);
            bean.setUserStatus(userStatus);
            bean.setApplyTime(new Date());
            int num = moneyApplyService.countOnlyOneMoneyApply(bean);
            if(0 == num){
                // 将代理员业务表中信息的提现状态变为已提现
                MoneyApply moneyApply = new MoneyApply();
                // 业绩奖
                Map<String,Object> mp = agentShareService.sumAgentMoney(agentId);
                Double sumMoney = (Double)mp.get("sumMoney");
                moneyApply.setLevel1Count(0); //代理员为了计算方便，设置为0，可以直接累加结果
                moneyApply.setUserId(agentId);
                moneyApply.setApplyMoney(sumMoney.floatValue());
                moneyApply.setApplyTime(new Date());
                // 审核状态（0：未审核，1：审核通过，2审核未通过）
                moneyApply.setCheckStatus(0);
                // 申请人身份（1：代理员；2：普通会员）
                moneyApply.setUserStatus(userStatus);
                int value = moneyApplyService.insertMoneyApplyForBean(moneyApply);
                result = BaseResult.checkResult(value);
            }else{
                result.put("code", "3");
                result.put("msg", "请不要重复提交申请");
            }
        } else {
            result.put("code", "2");
            result.put("msg", "代理员每月5号才能申请提现");
        }

        logger.info("agentWithdrawCashes---start" + result.toString());
        return result;
    }

    /**
     * 添加优惠券
     * 如果checkStatus=2(会员缴费成功)，
     * 若是第一次排队购车缴费成功，则为会员发放优惠券；
     * 若是第二次排队购车，即第一次使用优惠券，但优惠券已过期，则为会员方放优惠券；
     * 若是第二次排队购车，即第一次使用优惠券，且未过期，可以使用；
     * 若是第三次排队购车，即第二次使用优惠券，但优惠券已过期，则为会员方法优惠券（以后如此往复，直到优惠券被使用）；
     * 若是第三次排队购车，即第二次使用优惠券，若已使用过优惠券，则不再发放优惠券；
     */
    @RequestMapping(value = "insertVoucherInfo",method = RequestMethod.POST)
    public Map<String,Object> insertVoucherInfo(@RequestParam(value = "businessId",required = true) int businessId){
        //返回类型
        Map<String,Object> result = new HashMap<String,Object>();
        //获取业务信息，用户ID
        Business business = businessService.getBusinessById(businessId);
        int userId= business.getUserId();
        //获取优惠券状态，查看用户是否获取过优惠券/是否已过期/是否已使用
        Voucher vou = new Voucher();
        vou.setUserId(userId);
        List<Voucher> listVou = voucherService.listVoucherByBean(vou);
        int count = 0;
        //用户有优惠券，且优惠券都是 “未使用且已过期” 状态，则发放优惠券
        if( listVou.size()>0 ){
            for(Voucher vouche : listVou){
                if(2 == vouche.getVoucherStatus()){
                    count++;
                }
            }
            if(count > 0 && count == listVou.size()){
                result=  addVoucher(business);
            }else{
                result.put("code", BaseResult.SUCCESS_CODE);
                result.put("msg", BaseResult.SUCCESS_MSG);
            }
        }else{
            //用户从未获得过优惠券，发放优惠券
            result = addVoucher(business);
        }
        return result;
    }

    /**
     * 发放优惠券
     */
    @RequestMapping(value = "addVoucher",method = RequestMethod.POST)
    public Map<String,Object> addVoucher(
            @RequestParam(value = "business",required = true) Business business){
        //返回类型
        Map<String,Object> map = new HashMap<String,Object>();
        //获取参数
        int buyType = business.getBuyType(); //购车类型
        int userId= business.getUserId();    //会员ID
        Date payTime = business.getPayTime(); //缴费时间
        //发放优惠券
        Voucher bean = new Voucher();
        bean.setUserId(userId);
        bean.setVoucherMoney((2000f*buyType)); //优惠券金额
        bean.setVoucherStatus(1);
        bean.setVoucherTime(payTime);
        bean.setVoucherFinish(DateUtils.addDays(payTime, 30)); //获取到期时间，有效期30天
        bean.setIsDelete(0);
        int value = voucherService.insertVoucherByBean(bean);
        map = BaseResult.checkResult(value);
        return map;
    }

    /**
     * 头像上传
     */
    @RequestMapping(value="/uploadPhoto", method = RequestMethod.POST)
    public Map<String, Object> uploadPhoto(@RequestParam(value = "file", required = true) MultipartFile file,
                                           HttpServletRequest request) {
        logger.info("uploadPhoto-----------start---------");
        //获取用户
        Users session = (Users)request.getSession().getAttribute("user");
        Users users = userService.getUserById(session.getId());
        //返回类型
        Map<String, Object> result = new HashMap<String, Object>();
        //定义时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileTime = sdf.format(new Date());
        //上传文件的名称
        String fileName = file.getOriginalFilename();
        //上传文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        if (file.isEmpty()) {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
            return result;
        }
        //定义文件新名：以时间戳命名
        fileName = fileTime + suffixName;
        //定义实际保存位置
        String path = FileResourcePathUtil.propertyValueMap.get("photoDir") + users.getLoginName()+"/";
        File targetFile = new File(path);
        targetFile.mkdirs();
        File realFile = new File(path, fileName);
        try {
            //保存文件
            IOUtils.copy(file.getInputStream(),new FileOutputStream(realFile));
            result.put("path",path+fileName);
        } catch (IOException e) {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
            return result;
        }

        //同步数据库
        // 1>获取用户信息
        Users bean = userService.getUserById(users.getId());
        bean.setImage(path+fileName);
        // 2>更新数据库
        int value = userService.updateUserForBean(bean);
        if (value != 1) {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
            return result;
        }

        //同步session
     //   Users user = (Users)request.getSession().getAttribute("user");
        session.setImage(path+fileName);
        request.getSession().setAttribute("user", session);

        logger.info("updateUserInfo-----" + value + "----end" + result.toString());
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        return result;
    }

    /**
     * 会员显示的分享奖列表以及分享奖总额
     */
    @RequestMapping(value="/listShareMoney",method = RequestMethod.POST)
    public Map<String,Object> listShareMoney(
            @RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "pageNum", required = true) Integer pageNum,
            @RequestParam(value = "pageSize", required = true) Integer pageSize){
        logger.info("listShareMoney---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        int count = userRelateService.countShareMoney(userId);
        Map<String, Object> lstMap = userRelateService.listShareMoney(userId, pageNum, pageSize);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info", lstMap);
        result.put("total", count);
        logger.info("listShareMoney---end" + result.toString());
        return result;
    }

    /**
     * 直推会员的个数
     */
    @RequestMapping(value="/countLevel1",method = RequestMethod.POST)
    public Map<String,Object> countLevel1(
            @RequestParam(value = "userId", required = true) Integer userId){
        logger.info("countLevel1---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        int count = userRelateService.countLevel1(userId);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info", count);
        logger.info("countLevel1---end" + result.toString());
        return result;
    }

    /**
     * 推荐用户的二维码
     */
    @RequestMapping(value="/qrCode", method = RequestMethod.GET)
    @ResponseBody
    public void createQrCode(
            @RequestParam(value = "registerUrl", required = true) String registerUrl,
            @RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "phone", required = true) String phone,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        String url = getLogQrCodeContent(registerUrl,userId,phone);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = QRCodeUtils.genBarcode(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setContentType(QRCodeConstants.CONTENT_TYPE_PNG);
        try {
            if (bufferedImage != null) {
                ImageIO.write(bufferedImage, QRCodeConstants.TYPE_PNG, httpServletResponse.getOutputStream());
                logger.info("createQrCode success");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogQrCodeContent(String serverAddress, Integer userId, String phone) {
        StringBuilder comment = new StringBuilder();
        try {
            comment.append(serverAddress).append("?userId=").append(userId).append("&phone=").append(phone);
            logger.info("createQrCode qrcode " + comment.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comment.toString();
    }


    /**
     * 会员提现申请
     * @param userId 手机号
     * @param userStatus 会员名称
     */
    @RequestMapping(value="/withdrawCashes",method = RequestMethod.POST)
    public Map<String,Object> withdrawCashes(@RequestParam(value = "userId", required = true) Integer userId,
                                       @RequestParam(value = "userStatus", required = true) Integer userStatus){
        logger.info("user---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        // 首先判断提现的时间是否是星期一，如果不是星期一就不能继续提现操作
        Calendar cal= Calendar.getInstance();
        cal.setTime(new Date());
        int week = cal.get(Calendar.DAY_OF_WEEK)-1;
        if (week == 1) {
            // 判断是否重复提交
            MoneyApply bean = new MoneyApply();
            bean.setUserId(userId);
            bean.setUserStatus(userStatus);
            bean.setApplyTime(new Date());
            int num = moneyApplyService.countOnlyOneMoneyApply(bean);
            if(0 == num){
                // 将代理员业务表中信息的提现状态变为已提现
                MoneyApply moneyApply = new MoneyApply();
                // 直推会员人数
                int countLevel1 = userRelateService.countLevel1(userId);
                // 分享奖
                Map<String,Object> mp = userRelateService.sumShareMoney(userId);
                Double sumMoney = (Double)mp.get("sumMoney");
                // 进行提现申请时，如果直推会员达到20人就奖励5000
                if (countLevel1 >= 20) {
                    sumMoney = sumMoney + 5000f;
                    // 1代表直推人数超过20人状态标志
                    moneyApply.setLevel1Count(1);
                }
                moneyApply.setUserId(userId);
                moneyApply.setApplyMoney(sumMoney.floatValue());
                moneyApply.setApplyTime(new Date());
                // 审核状态（0：未审核，1：审核通过，2审核未通过）
                moneyApply.setCheckStatus(0);
                // 申请人身份（1：代理员；2：普通会员）
                moneyApply.setUserStatus(userStatus);
                int value = moneyApplyService.insertMoneyApplyForBean(moneyApply);
                if (value == 1) {
                    result.put("code", BaseResult.SUCCESS_CODE);
                    result.put("msg", BaseResult.SUCCESS_MSG);

                } else {
                    result.put("code", BaseResult.FAIL_CODE);
                    result.put("msg", BaseResult.FAIL_MSG);
                }
            }else{
                result.put("code", "3");
                result.put("msg", "请不要重复提交申请");
            }
        } else {
            result.put("code", "2");
            result.put("msg", "申请提现时间不是星期一，不允许提现");
        }

        logger.info("user---end" + result.toString());
        return result;
    }

    /**
     * 管理员查看申请提现的列表
     */
    @RequestMapping(value="/listMoneyApply",method = RequestMethod.POST)
    public Map<String,Object> listMoneyApply(
            @RequestParam(value = "pageNum", required = true) Integer pageNum,
            @RequestParam(value = "pageSize", required = true) Integer pageSize){
        logger.info("getWithdrawCashes---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        int count = moneyApplyService.countMoneyApplyForManager();
        List<MoneyApply> lstBean = moneyApplyService.listMoneyApplyForManager(pageNum, pageSize);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info", lstBean);
        result.put("total", count);
        logger.info("getWithdrawCashes---end" + result.toString());
        return result;
    }

    /**
     * 管理员查看申请提现详情
     * @param id MoneyApply表id
     */
    @RequestMapping(value="/getMoneyApplyDetail",method = RequestMethod.POST)
    public Map<String,Object> getMoneyApplyDetail(
            @RequestParam(value = "id", required = true) Integer id){
        logger.info("getMoneyApplyDetail---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Map<String, Object> moneyApply = moneyApplyService.getMoneyApplyByDetail(id);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info", moneyApply);
        logger.info("getMoneyApplyDetail---end" + result.toString());
        return result;
    }

    /**
     * 管理员对发起的提现进行审批
     * @param id id
     * @param checkStatus 审核状态
     */
    @RequestMapping(value="/approveWithdrawCashes",method = RequestMethod.POST)
    public Map<String,Object> approveWithdrawCashes(@RequestParam(value = "id", required = true) Integer id,
                                                    @RequestParam(value = "checkStatus", required = true) Integer checkStatus){
        logger.info("user---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        MoneyApply moneyApply = new MoneyApply();
        moneyApply.setId(id);
        moneyApply.setCreateTime(new Date());
        // 审核状态（0：未审核，1：审核通过，2审核未通过）
        moneyApply.setCheckStatus(checkStatus);
        int value = moneyApplyService.updateMoneyApplyForBean(moneyApply);
        if (value == 1) {
            // 提现审批通过之后，将users_relate表的对应记录标注已提现完成状态
            if (checkStatus == 1) {
                UsersRelate bean = new UsersRelate();
                bean.setUserId(moneyApplyService.getMoneyApplyById(id).getUserId());
                List<UsersRelate> lstUser = userRelateService.listUserRelateByBean(bean);
                for (UsersRelate relate : lstUser) {
                    UsersRelate param = new UsersRelate();
                    param.setId(relate.getId());
                    // 提现状态(0:未提现 1:已提现)
                    param.setApplyStatus(1);
                    int val = userRelateService.updateUserRelateForBean(param);
                    if (val == 1) {
                        result.put("code", BaseResult.SUCCESS_CODE);
                        result.put("msg", BaseResult.SUCCESS_MSG);
                    } else {
                        result.put("code", BaseResult.FAIL_CODE);
                        result.put("msg", BaseResult.FAIL_MSG);
                    }
                }
            }
        } else {
            result.put("code", BaseResult.FAIL_CODE);
            result.put("msg", BaseResult.FAIL_MSG);
        }
        logger.info("user---end" + result.toString());
        return result;
    }

    /**
     * 用户查看自己的提现明细列表
     */
    @RequestMapping(value="/getWithdrawCashes",method = RequestMethod.POST)
    public Map<String,Object> getWithdrawCashes(
            @RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "pageNum", required = true) Integer pageNum,
            @RequestParam(value = "pageSize", required = true) Integer pageSize){
        logger.info("getWithdrawCashes---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        MoneyApply bean = new MoneyApply();
        bean.setUserId(userId);
        int count = moneyApplyService.countMoneyApplyByBean(bean);
        List<MoneyApply> lstBean = moneyApplyService.listMoneyApplyByBean(bean, pageNum, pageSize);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info", lstBean);
        result.put("total", count);
        logger.info("getWithdrawCashes---end" + result.toString());
        return result;
    }

    /**
     * 我的会员
     */
    @RequestMapping(value="/myMember",method = RequestMethod.POST)
    public Map<String,Object> myMember(
            @RequestParam(value = "userId", required = true) Integer userId){
        logger.info("myMember---start");
        // 返回值
        Map<String,Object> result = new HashMap<String, Object>();
        Map<String, Object> data = userRelateService.listMyMember(userId);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg", BaseResult.SUCCESS_MSG);
        result.put("info", data);
        logger.info("myMember---end" + result.toString());
        return result;
    }
}
