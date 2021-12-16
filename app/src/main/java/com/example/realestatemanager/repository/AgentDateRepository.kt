package com.example.realestatemanager.repository

import androidx.lifecycle.LiveData
import com.example.realestatemanager.database.dao.AgentDao
import com.example.realestatemanager.model.Agent

class AgentDateRepository(private val agentDao: AgentDao) {

    fun getAgents(): LiveData<List<Agent>> { return agentDao.getAgents() }

    fun getAgent(agentId: Int): LiveData<Agent> { return agentDao.getAgent(agentId) }

    fun insertAgent(agent: Agent) = agentDao.insertAgent(agent)

    fun updateAgent(agent: Agent) = agentDao.updateAgent(agent)

    fun deleteAgent(agentId: Int) = agentDao.deleteAgent(agentId)

}