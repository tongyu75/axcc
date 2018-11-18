package com.axcc.dao;

import com.axcc.model.MoneyApply;

import java.util.List;
import java.util.Map;

public interface MoneyApplyDao {

    MoneyApply getMoneyApplyById(Integer id);

    Map<String, Object> getMoneyApplyByDetail(Integer id);

    MoneyApply getMoneyApplyByBean(MoneyApply bean);

    List<MoneyApply> listMoneyApplyByBean(MoneyApply bean);

    List<Map<String, Object>> getWithdrawCashes(MoneyApply bean);

    int countMoneyApplyByBean(MoneyApply bean);

    int countOnlyOneMoneyApply(MoneyApply bean);

    int insertMoneyApplyForBean(MoneyApply bean);

    int updateMoneyApplyForBean(MoneyApply bean);

    List<Map<String, Object>> listMoneyApplyForManager();

    int countMoneyApplyForManager();

    //----------------------------------------------------

    int deleteByPrimaryKey(Integer id);

    int insert(MoneyApply record);

    int insertSelective(MoneyApply record);

    MoneyApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MoneyApply record);

    int updateByPrimaryKey(MoneyApply record);
}