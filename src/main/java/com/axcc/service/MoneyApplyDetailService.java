package com.axcc.service;

import com.axcc.model.MoneyApply;
import com.axcc.model.MoneyApplyDetail;

import java.util.List;

/**
 * Created by tongshr on 2018/10/02.
 */
public interface MoneyApplyDetailService {
    int insertMoneyApplyDetailForBean(MoneyApplyDetail bean);

    int updateMoneyApplyDetailForBean(MoneyApplyDetail bean);

    List<MoneyApplyDetail> listMoneyApplyDetailByBean(MoneyApplyDetail bean, int pageNum, int pageSize);

    int countMoneyApplyDetailByBean(MoneyApplyDetail bean);
}
