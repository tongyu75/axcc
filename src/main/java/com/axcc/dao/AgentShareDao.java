package com.axcc.dao;

import com.axcc.model.AgentShare;

public interface AgentShareDao {

    AgentShare getAgentShareById(Integer id);

    AgentShare getAgentShareByBean(AgentShare bean);

    List<AgentShare> listAgentShareByBean(AgentShare bean);

    int countAgentShareByBean(AgentShare bean);

    int insertAgentShareForBean(AgentShare bean);

    int updateAgentShareForBean(AgentShare bean);

    //----------------------------------------------------

    int deleteByPrimaryKey(Integer id);

    int insert(AgentShare record);

    int insertSelective(AgentShare record);

    AgentShare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AgentShare record);

    int updateByPrimaryKey(AgentShare record);
}