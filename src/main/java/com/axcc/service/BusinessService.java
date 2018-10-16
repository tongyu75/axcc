package com.axcc.service;

import com.axcc.model.Business;

import java.util.List;

/**
 * Created by tongshr on 2018/10/02.
 */
public interface BusinessService {
    Business getBusinessById(int id);

    Business getBusinessByBean(Business bean);

    int insertBusinessForBean(Business bean);

    int updateBusinessForBean(Business bean);

    List<Business> listBusinessByBean(Business bean, int pageNum, int pageSize);

    int countBusinessByBean(Business bean);
}
