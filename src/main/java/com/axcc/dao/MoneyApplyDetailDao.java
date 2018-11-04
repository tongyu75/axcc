package com.axcc.dao;

import com.axcc.model.MoneyApply;
import com.axcc.model.MoneyApplyDetail;

import java.util.List;

public interface MoneyApplyDetailDao {

    MoneyApplyDetail getMoneyApplyDetailById(Integer id);

    MoneyApplyDetail getMoneyApplyDetailByBean(MoneyApplyDetail bean);

    List<MoneyApplyDetail> listMoneyApplyDetailByBean(MoneyApplyDetail bean);

    int countMoneyApplyDetailByBean(MoneyApplyDetail bean);

    int insertMoneyApplyDetailForBean(MoneyApplyDetail bean);

    int updateMoneyApplyDetailForBean(MoneyApplyDetail bean);
}