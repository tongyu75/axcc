package com.axcc.dao;

import com.axcc.model.AgentShare;

import java.util.List;
import java.util.Map;

public interface AgentShareDao {

    AgentShare getAgentShareById(Integer id);

    AgentShare getAgentShareByBean(AgentShare bean);

    List<AgentShare> listAgentShareByBean(AgentShare bean);

    List<Map<String, Object>> listAgentShareByAgentId(Integer agentId);

    int countAgentShareByAgentId(Integer agentId);

    int countAgentShareByBean(AgentShare bean);

    int insertAgentShareForBean(AgentShare bean);

    int updateAgentShareForBean(AgentShare bean);

    Map<String, Object> sumAgentShareMoney(Integer id);

    Map<String, Object> sumAgentMoney(Integer agentId);
}