package com.axcc.service.impl;

import com.axcc.dao.UsersDao;
import com.axcc.dao.UsersRelateDao;
import com.axcc.model.Users;
import com.axcc.model.UsersRelate;
import com.axcc.service.UserRelateService;
import com.axcc.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tongshr on 2018/10/02.
 */
@Service(value = "userRelateService")
public class UserRelateServiceImpl implements UserRelateService {

    @Resource
    private UsersRelateDao usersRelateDao;

    @Override
    public UsersRelate getUserRelateByBean(UsersRelate bean) {

        return usersRelateDao.getUserRelateByBean(bean);
    }

    @Override
    public UsersRelate getUserRelateById(int id) {
        return usersRelateDao.getUserRelateById(id);
    }

    @Override
    public int insertUserRelateForBean(UsersRelate bean) {

        return usersRelateDao.insertUserRelateForBean(bean);
    }

    @Override
    public int updateUserRelateForBean(UsersRelate bean) {
        return usersRelateDao.updateUserRelateForBean(bean);
    }

    @Override
    public int countUserRelateByBean(UsersRelate bean) {
        return usersRelateDao.countUserRelateByBean(bean);
    }

    @Override
    public List<UsersRelate> listUserRelateByBean(UsersRelate bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return usersRelateDao.listUserRelateByBean(bean);
    }

    @Override
    public List<UsersRelate> listUserRelateByBean(UsersRelate bean) {
        return usersRelateDao.listUserRelateByBean(bean);
    }

    /**
     * 获取分享奖信息
     * @param userId 当前会员original
     */
    @Override
    public Map<String, Object> listShareMoney(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> mp = new HashMap<>();
        List<Map<String, Object>> lstMap = usersRelateDao.listShareMoney(userId);
        // 我的分享奖（状态为未提现的状态）
        Map<String, Object> sumShareMoney = usersRelateDao.sumShareMoney(userId);
        mp.put("listShareMoney", lstMap);
        mp.put("sumShareMoney", sumShareMoney.get("sumMoney"));
        return mp;
    }

    /**
     * 我的分享奖
     * @param userId 当前会员original
     */
    @Override
    public Map<String, Object> sumShareMoney(Integer userId) {
        return usersRelateDao.sumShareMoney(userId);
    }

    /**
     * 获取分享奖数量
     * @param userId 当前会员original
     */
    @Override
    public int countShareMoney(Integer userId) {
        return usersRelateDao.countShareMoney(userId);
    }

    /**
     * 获取直推人数
     * @param userId 当前会员original
     */
    @Override
    public int countLevel1(Integer userId) {
        return usersRelateDao.countLeve1(userId);
    }

    /**
     * 我的会员
     * @param userId 当前会员original
     */
    @Override
    public Map<String, Object> listMyMember(Integer userId) {
        Map<String, Object> returnDate = new HashMap<>();
        // 直推会员
        List<Map<String, Object>> firstMap = new ArrayList<>();
        // 间推会员
        List<Map<String, Object>> secondMap = new ArrayList<>();
        // 间推会员
        List<Map<String, Object>> otherMap = new ArrayList<>();
        // 会员信息查询
        List<Map<String, Object>> listMap = usersRelateDao.listMyMember(userId);
        // 遍历会员信息
        for (Map<String, Object> mp : listMap) {
            String userName = (String) mp.get("userName");
            String loginName = (String) mp.get("loginName");
            int level = (Integer) mp.get("level");
            // 会员注册时间
            String createTime = (String) mp.get("createTime");
            // level为1代表是值推会员
            if (level == 1) {
                Map<String, Object> mpLevel1 = new HashMap<>();
                mpLevel1.put("level", "直推会员");
                mpLevel1.put("userName", userName);
                mpLevel1.put("loginName", loginName);
                mpLevel1.put("time", createTime);
                firstMap.add(mpLevel1);
                // 长度为2代表是间推会员
            } else if (level == 2) {
                Map<String, Object> mpLevel2 = new HashMap<>();
                mpLevel2.put("level", "间推会员");
                mpLevel2.put("userName", userName);
                mpLevel2.put("loginName", loginName);
                mpLevel2.put("time", createTime);
                secondMap.add(mpLevel2);
            } else {
                Map<String, Object> mpLevel3 = new HashMap<>();
                mpLevel3.put("level", "区间会员");
                mpLevel3.put("userName", userName);
                mpLevel3.put("loginName", loginName);
                mpLevel3.put("time", createTime);
                otherMap.add(mpLevel3);
            }
        }
        returnDate.put("level1Count", firstMap.size());
        returnDate.put("level1Data", firstMap);
        returnDate.put("level2Count", secondMap.size());
        returnDate.put("level2Data", secondMap);
        returnDate.put("level3Count", otherMap.size());
        returnDate.put("level3Data", otherMap);
        return returnDate;
    }
}
