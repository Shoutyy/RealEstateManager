package com.example.realestatemanager.repository

import androidx.lifecycle.LiveData
import com.example.realestatemanager.database.dao.AgentDao
import com.example.realestatemanager.model.Agent

class AgentDataRepository(private val agentDao: AgentDao) {

    fun getAgents(): LiveData<List<Agent>> = agentDao.getAgents()

    fun insertAgent(agent: Agent): Long = agentDao.insertAgent(agent)

    fun updateAgent(agent: Agent): Int = agentDao.updateAgent(agent)

}