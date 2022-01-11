package com.example.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.realestatemanager.model.Agent

@Dao
interface AgentDao {

    @Query("SELECT * FROM Agent")
    fun getAgents(): LiveData<List<Agent>>

}