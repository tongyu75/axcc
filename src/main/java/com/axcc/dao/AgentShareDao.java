package com.axcc.dao;

import com.axcc.model.AgentShare;

public interface AgentShareDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AgentShare record);

    int insertSelective(AgentShare record);

    AgentShare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AgentShare record);

    int updateByPrimaryKey(AgentShare record);
}