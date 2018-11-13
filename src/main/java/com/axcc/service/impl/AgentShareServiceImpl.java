package com.axcc.service.impl;

import com.axcc.dao.AgentShareDao;
import com.axcc.model.AgentShare;
import com.axcc.service.AgentShareService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tongshr on 2018/10/02.
 */
@Service(value = "agentShareService")
public class AgentShareServiceImpl implements AgentShareService {

    @Resource
    private AgentShareDao agentShareDao;

    @Override
    public AgentShare getAgentShareById(Integer id) {
        return agentShareDao.getAgentShareById(id);
    }

    @Override
    public AgentShare getAgentShareByBean(AgentShare bean) {
        return agentShareDao.getAgentShareByBean(bean);
    }

    @Override
    public List<AgentShare> listAgentShareByBean(AgentShare bean) {
        return agentShareDao.listAgentShareByBean(bean);
    }

    @Override
    public Map<String,Object> listAgentShareById(Integer agentId, int pageNum, int pageSize) {
        Map<String,Object> mp = new HashMap<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String,Object>> lstAgent = agentShareDao.listAgentShareByAgentId(agentId);
        mp.put("listAgentShare", lstAgent);
        Map<String, Object> sumShareMoney = agentShareDao.sumAgentShareMoney(agentId);
        mp.put("sumShareMoney", sumShareMoney.get("sumMoney"));
        return mp;
    }

    /**
     * 代理员业绩奖
     * @param agentId 当前代理员ID
     */
    @Override
    public Map<String,Object> sumAgentMoney(Integer agentId) {
        return agentShareDao.sumAgentMoney(agentId);
    }

    @Override
    public int countAgentShareById(Integer agentId) {
        return agentShareDao.countAgentShareByAgentId(agentId);
    }

    @Override
    public int insertAgentShareByBean(AgentShare bean) {
        return agentShareDao.insertAgentShareByBean(bean);
    }

    @Override
    public int updateAgentShareForBean(AgentShare bean) {
        return agentShareDao.updateAgentShareForBean(bean);
    }
}
