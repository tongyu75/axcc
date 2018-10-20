package com.axcc.dao;

import com.axcc.model.History;

import java.util.List;

public interface HistoryDao {

    History getHistoryById(Integer id);

    History getHistoryByBean(History bean);

    List<History> listHistoryByBean(History bean);

    int countHistoryByBean(History bean);

    int insertHistoryForBean(History bean);

    int updateHistoryForBean(History bean);

    //----------------------------------------------------
    
    int deleteByPrimaryKey(Integer id);

    int insert(History record);

    int insertSelective(History record);

    History selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(History record);

    int updateByPrimaryKey(History record);
}