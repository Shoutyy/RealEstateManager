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

    @Insert
    fun insertAgent(agent: Agent): Long

    @Update
    fun updateAgent(agent: Agent): Int

}