package com.axcc.service;

import com.axcc.model.AgentShare;
import com.axcc.model.Users;

import java.util.List;
import java.util.Map;

/**
 * Created by tongshr on 2018/10/02.
 */
public interface AgentShareService {

    AgentShare getAgentShareById(Integer id);

    AgentShare getAgentShareByBean(AgentShare bean);

    List<AgentShare> listAgentShareByBean(AgentShare bean);

    Map<String,Object> listAgentShareById(Integer agentId, int pageNum, int pageSize);

    int countAgentShareById(Integer agentId);

    int insertAgentShareForBean(AgentShare bean);

    int updateAgentShareForBean(AgentShare bean);

}
