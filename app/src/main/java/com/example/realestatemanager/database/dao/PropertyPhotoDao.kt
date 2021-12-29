package com.example.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import android.database.sqlite.SQLiteConstraintException
import androidx.room.*
import com.example.realestatemanager.model.PropertyPhoto

@Dao
interface PropertyPhotoDao {

    @Query("Select * FROM PropertyPhoto WHERE :id == property_photo_id AND :isThisTheIllustration == isThisTheIllustration")
    fun getIllustrationPropertyPhoto(id: Int, isThisTheIllustration: Boolean): LiveData<PropertyPhoto>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertPropertyPhoto(propertyPhoto: PropertyPhoto): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updatePropertyPhoto(propertyPhoto: PropertyPhoto)

    @Query("DELETE FROM PropertyPhoto WHERE :id == property_photo_id")
    fun deletePropertyPhoto(id: Int)

}