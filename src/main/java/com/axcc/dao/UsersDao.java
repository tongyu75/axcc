package com.axcc.dao;

import com.axcc.model.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsersDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectUserByPrimaryKey(Integer id);

    Users selectUserByBean(Users record);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    List<Users> selectAllUser();
}