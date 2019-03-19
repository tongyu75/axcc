package com.axcc.dao;

import com.axcc.model.Business;
import com.axcc.model.BusinessUser;

import java.util.List;
import java.util.Map;

public interface BusinessDao {

    Business getBusinessById(Integer id);

    BusinessUser getBusinessUsersByUserId(Integer userId);

    int getBuyStatusCount();

    Business getBusinessByBean(Business record);

    List<Business> listBusinessByBean(Business bean);

    int countBusinessByBean(Business bean);

    BusinessUser getBusinessUserById(Integer id);

    BusinessUser getBusinessUserByBean(BusinessUser record);

    List<BusinessUser> listBusinessUserByBean(BusinessUser bean);

    /**会员登录，获取所有已排队、出车的用户信息*/
    List<BusinessUser> listBusinessUser(BusinessUser bean);

    int countBusinessUserByBean(BusinessUser bean);
    /**管理员登录，获取会员申请列表*/
    List<BusinessUser> listApplyUserByBean(BusinessUser bean);

    int countBusinessUserWaitOrOut(BusinessUser bean);

    int countApplyUserByBean(BusinessUser bean);

    List<Map<String,Object>> listAllUserByBean(BusinessUser bean);

    int countAllUserList(BusinessUser bean);

    List<BusinessUser> listBusinessByAgent(BusinessUser bean);

    int countBusinessByAgent(BusinessUser bean);

    int insertBusinessForBean(Business record);

    int updateBusinessForBean(Business record);

    int deleteByUserId(Integer userId);

    int deleteByPrimaryKey(Integer id);

    int insert(Business record);

    int insertSelective(Business record);

    Business selectByPrimaryKey(Integer id);
}