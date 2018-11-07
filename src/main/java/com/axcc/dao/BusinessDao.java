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

    int countBusinessUserByBean(BusinessUser bean);

    List<Map<String,Object>> listAllUserByBean(BusinessUser bean);

    int countAllUserList(BusinessUser bean);

    List<BusinessUser> listBusinessByAgent(BusinessUser bean);

    int countBusinessByAgent(BusinessUser bean);

    int insertBusinessForBean(Business record);

    int updateBusinessForBean(Business record);

    int deleteByPrimaryKey(Integer id);

    int insert(Business record);

    int insertSelective(Business record);

    Business selectByPrimaryKey(Integer id);
}