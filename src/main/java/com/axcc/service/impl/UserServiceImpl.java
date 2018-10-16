package com.axcc.service.impl;

import com.axcc.dao.UsersDao;
import com.axcc.model.Users;
import com.axcc.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Override
    public List<Map<String,Object>> findAllApply(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        return usersDao.findAllApply();
    }
}
