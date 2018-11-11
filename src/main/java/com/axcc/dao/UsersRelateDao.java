package com.axcc.dao;

import com.axcc.model.UsersRelate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UsersRelateDao {

    UsersRelate getUserRelateById(Integer id);

    UsersRelate getUserRelateByBean(UsersRelate record);

    List<UsersRelate> listUserRelateByBean(UsersRelate bean);

    int countUserRelateByBean(UsersRelate bean);

    int insertUserRelateForBean(UsersRelate record);

    int updateUserRelateForBean(UsersRelate record);

    List<Map<String, Object>> listShareMoney(Integer id);

    int countShareMoney(Integer id);

    int countLeve1(Integer id);

    Map<String, Object> sumShareMoney(Integer id);

    List<Map<String, Object>> listMyMember(Integer id);

}