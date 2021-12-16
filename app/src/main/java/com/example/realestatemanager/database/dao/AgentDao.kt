package com.example.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.realestatemanager.model.Agent

@Dao
interface AgentDao {

    @Query("SELECT * FROM Agent")
    fun getAgents(): LiveData<List<Agent>>

    @Query("SELECT * FROM Agent WHERE :id == agent_id")
    fun getAgent(id: Int): LiveData<Agent>

    @Insert
    fun insertAgent(agent: Agent)

    @Update
    fun updateAgent(agent: Agent)

    @Query("DELETE FROM Agent WHERE :id == agent_id")
    fun deleteAgent(id: Int)

    /*@Delete("DELETE * FROM Agent WHERE :id == agent_id")
    fun deleteAgent(id: Int)*/

}