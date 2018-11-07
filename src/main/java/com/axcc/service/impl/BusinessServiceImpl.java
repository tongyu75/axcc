package com.axcc.service.impl;

import com.axcc.dao.BusinessDao;
import com.axcc.model.Business;
import com.axcc.model.BusinessUser;
import com.axcc.service.BusinessService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by tongshr on 2018/10/02.
 */
@Service(value = "Businesservice")
public class BusinessServiceImpl implements BusinessService {

    @Resource
    private BusinessDao BusinessDao;

    @Override
    public Business getBusinessByBean(Business bean) {

        return BusinessDao.getBusinessByBean(bean);
    }

    @Override
    public Business getBusinessById(int id) {
        return BusinessDao.getBusinessById(id);
    }

    @Override
    public int insertBusinessForBean(Business bean) {

        return BusinessDao.insertBusinessForBean(bean);
    }

    @Override
    public int updateBusinessForBean(Business bean) {
        return BusinessDao.updateBusinessForBean(bean);
    }

    @Override
    public int countBusinessByBean(Business bean) {
        return BusinessDao.countBusinessByBean(bean);
    }

    @Override
    public List<Business> listBusinessByBean(Business bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return BusinessDao.listBusinessByBean(bean);
    }

    @Override
    public BusinessUser getBusinessUserByBean(BusinessUser bean) {

        return BusinessDao.getBusinessUserByBean(bean);
    }

    @Override
    public BusinessUser getBusinessUserById(int id) {
        return BusinessDao.getBusinessUserById(id);
    }

    @Override
    public BusinessUser getBusinessUsersByUserId(Integer userId) {
        return BusinessDao.getBusinessUsersByUserId(userId);
    }

    @Override
    public int getBuyStatusCount() {
        return BusinessDao.getBuyStatusCount();
    }

    @Override
    public int countBusinessUserByBean(BusinessUser bean) {
        return BusinessDao.countBusinessUserByBean(bean);
    }

    @Override
    public List<BusinessUser> listBusinessUserByBean(BusinessUser bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return BusinessDao.listBusinessUserByBean(bean);
    }
    @Override
    public int countAllUserList(BusinessUser bean) {
        return BusinessDao.countAllUserList(bean);
    }

    @Override
    public List<Map<String,Object>> listAllUserByBean(BusinessUser bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String,Object>> listMap = BusinessDao.listAllUserByBean(bean);
        for(Map<String,Object> map : listMap){
            System.out.println(map);
            if(null == map.get("buyType")){
                map.put("buyType","未申请购车");
            }
            if(null == map.get("buyMoney")){
                map.put("buyMoney","无");
            }
            if(null == map.get("buyStatus")){
                map.put("buyStatus","未申请购车");
            }
            if(null == map.get("checkStatus")){
                map.put("checkStatus","无");
            }
            if(null == map.get("waitNum")){
                map.put("waitNum","无");
            }
        }
        return listMap;
    }

    @Override
    public int countBusinessByAgent(BusinessUser bean) {
        return BusinessDao.countBusinessByAgent(bean);
    }

    @Override
    public List<BusinessUser> listBusinessByAgent(BusinessUser bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return BusinessDao.listBusinessByAgent(bean);
    }
}
