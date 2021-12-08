package com.example.realestatemanager.database.converter

import androidx.room.TypeConverter
import com.example.realestatemanager.model.Agent

class AgentConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromAgent(agent: Agent): Int {
            return agent.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toAgent(int: Int): Agent {
            return Agent.values()[int]
        }
    }
}