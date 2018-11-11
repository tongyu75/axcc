package com.axcc.service;

import com.axcc.model.MoneyApply;

import java.util.List;

/**
 * Created by tongshr on 2018/10/02.
 */
public interface MoneyApplyService {

    MoneyApply getMoneyApplyById(int id);

    int insertMoneyApplyForBean(MoneyApply bean);

    int updateMoneyApplyForBean(MoneyApply bean);

    List<MoneyApply> listMoneyApplyByBean(MoneyApply bean, int pageNum, int pageSize);

    int countMoneyApplyByBean(MoneyApply bean);

    int countOnlyOneMoneyApply(MoneyApply bean);

    List<MoneyApply> listMoneyApplyForManager(int pageNum, int pageSize);

    int countMoneyApplyForManager();
}
