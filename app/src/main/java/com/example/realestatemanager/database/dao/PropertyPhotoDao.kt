package com.example.realestatemanager.database.dao

import androidx.room.*
import com.example.realestatemanager.model.PropertyPhoto

@Dao
interface PropertyPhotoDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertPropertyPhoto(propertyPhoto: PropertyPhoto): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updatePropertyPhoto(propertyPhoto: PropertyPhoto): Int

    @Query("DELETE FROM PropertyPhoto WHERE :id == property_photo_id")
    fun deletePropertyPhoto(id: Int)

}