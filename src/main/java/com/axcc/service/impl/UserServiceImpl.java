package com.axcc.service.impl;

import com.axcc.dao.UsersDao;
import com.axcc.model.Users;
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
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UsersDao usersDao;

    @Override
    public Users getUserByBean(Users bean) {

        return usersDao.getUserByBean(bean);
    }

    @Override
    public Users getUserById(int id) {
        return usersDao.getUserById(id);
    }

    @Override
    public Users getUserByLoginName(String loginName) {
        return usersDao.getUserByLoginName(loginName);
    }

    @Override
    public int resetPassword(Users bean) {

        return usersDao.resetPassword(bean);
    }

    @Override
    public int insertUserForBean(Users bean) {

        return usersDao.insertUserForBean(bean);
    }

    @Override
    public int updateUserForBean(Users bean) {
        return usersDao.updateUserForBean(bean);
    }

    @Override
    public int countUserByBean(Users bean) {
        return usersDao.countUserByBean(bean);
    }

    @Override
    public List<Users> listUserByBean(Users bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return usersDao.listUserByBean(bean);
    }

    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    @Override
    public List<Users> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        return usersDao.selectAllUser();
    }
    /**
     * 多表查询，获取所有会员申请单列表信息
     *
     */
 /*   @Override
    public List<Map<String,Object>> findAllApply(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        return usersDao.;
    }*/

    /**
     * 获取分享奖数量
     * @param original 当前会员original
     */
    @Override
    public int countShareMoney(String original) {
        return usersDao.countShareMoney(String.valueOf(original));
    }

    /**
     * 获取直推人数
     * @param original 当前会员original
     */
    @Override
    public int countLevel1(String original) {
        return usersDao.countLeve1(String.valueOf(original));
    }

    /**
     * 获取分享奖信息
     * @param original 当前会员original
     */
    @Override
    public List<Map<String, Object>> listShareMoney(String original, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> returnDate = new ArrayList<>();
        // 直推会员
        List<Map<String, Object>> firstMap = new ArrayList<>();
        // 间推会员
        List<Map<String, Object>> secondMap = new ArrayList<>();
        // 会员信息查询
        List<Map<String, Object>> listMap = usersDao.listShareMoney(original);
        // 遍历会员信息
        for (Map<String, Object> mp : listMap) {
            String userName = (String) mp.get("userName");
            String originalValue = (String) mp.get("original");
            float buyMoney = (float) mp.get("buyMoney");
            String checkTime = (String) mp.get("checkTime");
            // 数据例子：直推会员:32-33, 间推会员：32-33-34，其中32表示当前的会员
            String[] data = originalValue.replace(originalValue + "-", "").split("-");
            // data长度为1代表是值推会员
            if (data.length == 1) {
                Map<String, Object> mpLevel1 = new HashMap<>();
                mpLevel1.put("level", "直推会员");
                mpLevel1.put("userName", userName);
                mpLevel1.put("money", buyMoney * 0.05f);
                mpLevel1.put("time", checkTime);
                firstMap.add(mpLevel1);
                // 长度为2代表是间推会员
            } else if (data.length == 2) {
                Map<String, Object> mpLevel2 = new HashMap<>();
                mpLevel2.put("level", "间推会员");
                mpLevel2.put("userName", userName);
                mpLevel2.put("money", buyMoney * 0.03f);
                mpLevel2.put("time", checkTime);
                secondMap.add(mpLevel2);
            }
        }

        // 将上面获取的信息按照直推会员和间推会员进行排序，然后返回。
        for (Map<String, Object> mp : firstMap) {
            returnDate.add(mp);
        }
        for (Map<String, Object> mp : secondMap) {
            returnDate.add(mp);
        }

        return returnDate;
    }
}
