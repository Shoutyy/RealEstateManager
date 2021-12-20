package com.example.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.realestatemanager.model.PropertyAndLocationOfInterest

@Dao
interface PropertyAndLocationOfInterestDao {

    @Query("SELECT * FROM PropertyAndLocationOfInterest WHERE :id == propertyId")
    fun getLocationsOfInterest(id: Int): LiveData<List<PropertyAndLocationOfInterest>>

    @Insert
    fun insertLocationOfInterest(propertyAndLocationOfInterest: PropertyAndLocationOfInterest)

    @Update
    fun updateLocationOfInterest(propertyAndLocationOfInterest: PropertyAndLocationOfInterest)

    @Query("DELETE FROM PropertyAndLocationOfInterest WHERE :id == propertyId")
    fun deleteLocationOfInterest(id: Int)

}