package com.axcc.service.impl;

import com.axcc.dao.UsersDao;
import com.axcc.model.Users;
import com.axcc.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tongshr on 2018/10/02.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UsersDao usersDao;

    @Override
    public Users selectUserByBean(Users bean) {
        return usersDao.selectUserByBean(bean);
    }


    @Override
    public Users selectUserById(int id) {
        return usersDao.selectUserByPrimaryKey(id);
    }

    @Override
    public int resetPassword(Users bean) {
        return usersDao.resetPassword(bean);
    }

    @Override
    public int insertUserForBean(Users bean) {
        return usersDao.insertUserForBean(bean);
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
}
