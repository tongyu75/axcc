package com.axcc.dao;

import com.axcc.model.MoneyApply;

import java.util.List;

public interface MoneyApplyDao {

    MoneyApply getMoneyApplyById(Integer id);

    MoneyApply getMoneyApplyByBean(MoneyApply bean);

    List<MoneyApply> listMoneyApplyByBean(MoneyApply bean);

    int countMoneyApplyByBean(MoneyApply bean);

    int countOnlyOneMoneyApply(MoneyApply bean);

    int insertMoneyApplyForBean(MoneyApply bean);

    int updateMoneyApplyForBean(MoneyApply bean);

    List<MoneyApply> listMoneyApplyForManager();

    int countMoneyApplyForManager();

    //----------------------------------------------------

    int deleteByPrimaryKey(Integer id);

    int insert(MoneyApply record);

    int insertSelective(MoneyApply record);

    MoneyApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MoneyApply record);

    int updateByPrimaryKey(MoneyApply record);
}