package com.example.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.realestatemanager.model.Property

@Dao
interface PropertyDao {

    @Query("SELECT * FROM Property INNER JOIN Address ON Property.addressId = Address.address_id INNER JOIN Agent ON Property.agentId = Agent.agent_id")
    fun getAllProperties(): LiveData<List<Property>>

    @Query("SELECT * FROM Property INNER JOIN Address ON Property.addressId = Address.address_id INNER JOIN Agent ON Property.agentId = Agent.agent_id WHERE :id == id")
    fun getProperty(id: Int): LiveData<Property>

    @Insert
    fun insertProperty(property: Property): Long

    @Update
    fun updateProperty(property: Property)

    @RawQuery(observedEntities = [Property::class])
    fun customSearchPropertiesId(query: SimpleSQLiteQuery): LiveData<List<Int>>

    @RawQuery(observedEntities = [Property::class])
    fun getProperties(query: SimpleSQLiteQuery): LiveData<List<Property>>

}