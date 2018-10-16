package com.axcc.service.impl;

import com.axcc.dao.BusinessDao;
import com.axcc.model.Business;
import com.axcc.service.BusinessService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tongshr on 2018/10/02.
 */
@Service(value = "Businesservice")
public class BusinessServiceImpl implements BusinessService {

    @Resource
    private BusinessDao BusinessDao;

    @Override
    public Business getBusinessByBean(Business bean) {

        return BusinessDao.getBusinessByBean(bean);
    }

    @Override
    public Business getBusinessById(int id) {
        return BusinessDao.getBusinessById(id);
    }

    @Override
    public int insertBusinessForBean(Business bean) {

        return BusinessDao.insertBusinessForBean(bean);
    }

    @Override
    public int updateBusinessForBean(Business bean) {
        return BusinessDao.updateBusinessForBean(bean);
    }

    @Override
    public int countBusinessByBean(Business bean) {
        return BusinessDao.countBusinessByBean(bean);
    }

    @Override
    public List<Business> listBusinessByBean(Business bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return BusinessDao.listBusinessByBean(bean);
    }
}
