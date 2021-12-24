package com.example.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.realestatemanager.model.PropertyPhoto

@Dao
interface PropertyPhotoDao {

    @Query("Select * FROM PropertyPhoto WHERE :id == property_photo_id AND :isThisTheIllustration == isThisTheIllustration")
    fun getIllustrationPropertyPhoto(id: Int, isThisTheIllustration: Boolean): LiveData<PropertyPhoto>

    @Insert
    fun insertPropertyPhoto(propertyPhoto: PropertyPhoto): Long

    @Update
    fun updatePropertyPhoto(propertyPhoto: PropertyPhoto)

    @Query("DELETE FROM PropertyPhoto WHERE :id == property_photo_id")
    fun deletePropertyPhoto(id: Int)

}