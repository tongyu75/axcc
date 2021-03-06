package com.axcc.dao;

import com.axcc.model.MobileCode;
import com.axcc.model.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UsersDao {

    Users getUserById(Integer id);

    Users getUserByBean(Users record);

    List<Users> listUserByBean(Users bean);

    int countUserByBean(Users bean);

    int insertUserForBean(Users record);

    int updateUserForBean(Users record);

    Users getUserByLoginName(String loginName);

    int resetPassword(Users record);

    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    List<Users> selectAllUser();

    int insertMobileCode(MobileCode mobileCode);

    MobileCode selectMobileCode(String loginName);

}