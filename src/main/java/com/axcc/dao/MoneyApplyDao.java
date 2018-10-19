package com.axcc.dao;

import com.axcc.model.MoneyApply;

public interface MoneyApplyDao {

    MoneyApply getMoneyApplyById(Integer id);

    MoneyApply getMoneyApplyByBean(MoneyApply bean);

    List<MoneyApply> listMoneyApplyByBean(MoneyApply bean);

    int countMoneyApplyByBean(MoneyApply bean);

    int insertMoneyApplyForBean(MoneyApply bean);

    int updateMoneyApplyForBean(MoneyApply bean);

    //----------------------------------------------------
    
    int deleteByPrimaryKey(Integer id);

    int insert(MoneyApply record);

    int insertSelective(MoneyApply record);

    MoneyApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MoneyApply record);

    int updateByPrimaryKey(MoneyApply record);
}