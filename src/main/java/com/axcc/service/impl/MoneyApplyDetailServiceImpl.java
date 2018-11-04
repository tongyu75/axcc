package com.axcc.service.impl;

import com.axcc.model.MoneyApplyDetail;
import com.axcc.service.MoneyApplyDetailService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tongshr on 2018/10/02.
 */
@Service(value = "moneyApplyDetailService")
public class MoneyApplyDetailServiceImpl implements MoneyApplyDetailService {

    @Resource
    private com.axcc.dao.MoneyApplyDetailDao MoneyApplyDetailDao;

    @Override
    public int insertMoneyApplyDetailForBean(MoneyApplyDetail bean) {
        return MoneyApplyDetailDao.insertMoneyApplyDetailForBean(bean);
    }

    @Override
    public int updateMoneyApplyDetailForBean(MoneyApplyDetail bean) {
        return MoneyApplyDetailDao.updateMoneyApplyDetailForBean(bean);
    }

    @Override
    public List<MoneyApplyDetail> listMoneyApplyDetailByBean(MoneyApplyDetail bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return MoneyApplyDetailDao.listMoneyApplyDetailByBean(bean);
    }

    @Override
    public int countMoneyApplyDetailByBean(MoneyApplyDetail bean) {
        return MoneyApplyDetailDao.countMoneyApplyDetailByBean(bean);
    }

}
