package com.axcc.service;

import com.axcc.model.MoneyApply;

import java.util.List;
import java.util.Map;

/**
 * Created by tongshr on 2018/10/02.
 */
public interface MoneyApplyService {

    MoneyApply getMoneyApplyById(int id);

    Map<String, Object> getMoneyApplyByDetail(int id);

    int insertMoneyApplyForBean(MoneyApply bean);

    int updateMoneyApplyForBean(MoneyApply bean);

    List<MoneyApply> listMoneyApplyByBean(MoneyApply bean, int pageNum, int pageSize);

    List<Map<String, Object>> getWithdrawCashes(MoneyApply bean, int pageNum, int pageSize);

    int countMoneyApplyByBean(MoneyApply bean);

    int countOnlyOneMoneyApply(MoneyApply bean);

    List<Map<String, Object>> listMoneyApplyForManager(int pageNum, int pageSize);

    int countMoneyApplyForManager();
}
