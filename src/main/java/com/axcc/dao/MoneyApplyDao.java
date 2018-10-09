package com.axcc.dao;

import com.axcc.model.MoneyApply;

public interface MoneyApplyDao {
    int deleteByPrimaryKey(Integer id);

    int insert(MoneyApply record);

    int insertSelective(MoneyApply record);

    MoneyApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MoneyApply record);

    int updateByPrimaryKey(MoneyApply record);
}