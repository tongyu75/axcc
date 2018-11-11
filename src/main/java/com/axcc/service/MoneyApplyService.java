package com.axcc.service;

import com.axcc.model.MoneyApply;
import com.axcc.model.Users;
import com.axcc.model.UsersRelate;

import java.util.List;
import java.util.Map;

/**
 * Created by tongshr on 2018/10/02.
 */
public interface MoneyApplyService {

    MoneyApply getMoneyApplyById(int id);

    int insertMoneyApplyForBean(MoneyApply bean);

    int updateMoneyApplyForBean(MoneyApply bean);

    List<MoneyApply> listMoneyApplyByBean(MoneyApply bean, int pageNum, int pageSize);

    int countMoneyApplyByBean(MoneyApply bean);

    List<MoneyApply> listMoneyApplyForManager(int pageNum, int pageSize);

    int countMoneyApplyForManager();
}
