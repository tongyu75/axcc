package com.axcc.service;

import com.axcc.model.Business;
import com.axcc.model.BusinessUser;

import java.util.List;
import java.util.Map;

/**
 * Created by tongshr on 2018/10/02.
 */
public interface BusinessService {

    Business getBusinessById(int id);

    Business getBusinessByBean(Business bean);

    BusinessUser getBusinessUsersByUserId(Integer userId);

    int getBuyStatusCount();

    int insertBusinessForBean(Business bean);

    int updateBusinessForBean(Business bean);

    int deleteByUserId(Integer userId);

    List<Business> listBusinessByBean(Business bean, int pageNum, int pageSize);

    int countBusinessByBean(Business bean);

    BusinessUser getBusinessUserById(int id);

    BusinessUser getBusinessUserByBean(BusinessUser bean);
    /**管理员登录，获取会员排队列表*/
    List<BusinessUser> listBusinessUserByBean(BusinessUser bean, int pageNum, int pageSize);

    /**会员登录，获取所有已排队、出车的用户信息*/
    List<BusinessUser> listBusinessUser(BusinessUser bean, int pageNum, int pageSize);

    int countBusinessUserByBean(BusinessUser bean);
    /**管理员登录，获取会员申请列表*/
    List<BusinessUser> listApplyUserByBean(BusinessUser bean, int pageNum, int pageSize);

    int countApplyUserByBean(BusinessUser bean);

    List<Map<String,Object>> listAllUserByBean(BusinessUser bean, int pageNum, int pageSize);

    int countAllUserList(BusinessUser bean);

    List<BusinessUser> listBusinessByAgent(BusinessUser bean, int pageNum, int pageSize);

    int countBusinessByAgent(BusinessUser bean);
}
