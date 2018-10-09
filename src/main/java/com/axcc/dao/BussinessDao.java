package com.axcc.dao;

import com.axcc.model.Bussiness;

public interface BussinessDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Bussiness record);

    int insertSelective(Bussiness record);

    Bussiness selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bussiness record);

    int updateByPrimaryKey(Bussiness record);
}