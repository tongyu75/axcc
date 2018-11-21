package com.axcc.service.impl;

import com.axcc.dao.BusinessDao;
import com.axcc.model.Business;
import com.axcc.model.BusinessUser;
import com.axcc.service.BusinessService;
import com.axcc.utils.BaseResult;
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
    public int deleteByUserId(Integer userId){
        return BusinessDao.deleteByUserId(userId);
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
    public List<BusinessUser> listApplyUserByBean(BusinessUser bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return BusinessDao.listApplyUserByBean(bean);
    }

    @Override
    public int countApplyUserByBean(BusinessUser bean) {
        return BusinessDao.countApplyUserByBean(bean);
    }

    @Override
    public List<BusinessUser> listBusinessUserByBean(BusinessUser bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return BusinessDao.listBusinessUserByBean(bean);
    }
    @Override
    public List<BusinessUser> listBusinessUser(BusinessUser bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return BusinessDao.listBusinessUser(bean);
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
            //------------------购车类型-----------------------
            if(null == map.get("buyType")){
                map.put("buyType","未申请购车");
            }else{
                int bType = (int)map.get("buyType");
                if(1 == bType){
                    map.put("buyType", BaseResult.BUYTYPE_1);
                }else if(2 == bType){
                    map.put("buyType", BaseResult.BUYTYPE_2);
                }else if(3 == bType){
                    map.put("buyType", BaseResult.BUYTYPE_3);
                }else if(5 == bType){
                    map.put("buyType", BaseResult.BUYTYPE_5);
                }
            }
            //------------------购车金额------------------------
            if(null == map.get("buyMoney")){
                map.put("buyMoney","无");
            }
            //------------------购车状态------------------------
            if(null == map.get("buyStatus")){
                map.put("buyStatus","未申请购车");
            }else{
                int bStatus = (int)map.get("buyStatus");
                if(0 == bStatus){
                    map.put("buyStatus",BaseResult.BUYSTATUS_0);
                }else if(1 == bStatus){
                    map.put("buyStatus",BaseResult.BUYSTATUS_1);
                }else if(2 == bStatus){
                    map.put("buyStatus",BaseResult.BUYSTATUS_2);
                }
            }
            //------------------审核状态------------------------
            if(null == map.get("checkStatus")){
                map.put("checkStatus","无");
            }else{
                int chStatus = (int)map.get("checkStatus");
                if (0 == chStatus){
                    map.put("checkStatus",BaseResult.CHECKSTATUS_0);
                }
                else if (1 == chStatus){
                    map.put("checkStatus",BaseResult.CHECKSTATUS_1);
                }
                else if (2 == chStatus){
                    map.put("checkStatus",BaseResult.CHECKSTATUS_2);
                }
                else if (3 == chStatus){
                    map.put("checkStatus",BaseResult.CHECKSTATUS_3);
                }
                else if (4 == chStatus){
                    map.put("checkStatus",BaseResult.CHECKSTATUS_4);
                }
            }
            //-------------------排队号码------------------------
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
