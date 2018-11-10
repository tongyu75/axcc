package com.axcc.service;

import com.axcc.model.Users;
import com.axcc.model.UsersRelate;

import java.util.List;
import java.util.Map;

/**
 * Created by tongshr on 2018/10/02.
 */
public interface UserRelateService {
    UsersRelate getUserRelateById(int id);

    UsersRelate getUserRelateByBean(UsersRelate bean);

    int insertUserRelateForBean(UsersRelate bean);

    int updateUserRelateForBean(UsersRelate bean);

    List<UsersRelate> listUserRelateByBean(UsersRelate bean, int pageNum, int pageSize);

    List<UsersRelate> listUserRelateByBean(UsersRelate bean);

    int countUserRelateByBean(UsersRelate bean);

    Map<String, Object> sumShareMoney(Integer userId);

    Map<String, Object> listShareMoney(Integer userId, int pageNum, int pageSize);

    int countShareMoney(Integer userId);

    int countLevel1(Integer userId);

    Map<String, Object> listMyMember(Integer userId);
}
