package com.axcc.service;

import com.axcc.model.Users;

import java.util.List;

/**
 * Created by tongshr on 2018/10/02.
 */
public interface UserService {
    Users selectUserById(int id);

    List<Users> findAllUser(int pageNum, int pageSize);
}
