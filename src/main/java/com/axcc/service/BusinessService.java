package com.axcc.service;

import com.axcc.model.Business;
import com.axcc.model.BusinessUser;

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

    BusinessUser getBusinessUserById(int id);

    BusinessUser getBusinessUserByBean(BusinessUser bean);

    List<BusinessUser> listBusinessUserByBean(BusinessUser bean, int pageNum, int pageSize);

    int countBusinessUserByBean(BusinessUser bean);
}
