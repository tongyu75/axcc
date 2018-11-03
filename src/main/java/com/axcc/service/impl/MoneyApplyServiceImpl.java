package com.axcc.service.impl;

import com.axcc.dao.MoneyApplyDao;
import com.axcc.dao.UsersDao;
import com.axcc.model.MoneyApply;
import com.axcc.model.Users;
import com.axcc.service.MoneyApplyService;
import com.axcc.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tongshr on 2018/10/02.
 */
@Service(value = "moneyApplyService")
public class MoneyApplyServiceImpl implements MoneyApplyService {

    @Resource
    private MoneyApplyDao moneyApplyDao;

    @Override
    public int insertMoneyApplyForBean(MoneyApply bean) {
        return moneyApplyDao.insertMoneyApplyForBean(bean);
    }

    @Override
    public int updateMoneyApplyForBean(MoneyApply bean) {
        return moneyApplyDao.updateMoneyApplyForBean(bean);
    }

    @Override
    public List<MoneyApply> listMoneyApplyByBean(MoneyApply bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return moneyApplyDao.listMoneyApplyByBean(bean);
    }

    @Override
    public int countMoneyApplyByBean(MoneyApply bean) {
        return moneyApplyDao.countMoneyApplyByBean(bean);
    }

}
