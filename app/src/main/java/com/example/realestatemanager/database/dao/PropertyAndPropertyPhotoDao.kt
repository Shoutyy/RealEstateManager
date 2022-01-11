package com.example.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.realestatemanager.model.PropertyAndPropertyPhoto

@Dao
interface PropertyAndPropertyPhotoDao {

    @Query("SELECT * FROM PropertyAndPropertyPhoto INNER JOIN PropertyPhoto ON PropertyAndPropertyPhoto.propertyPhotoId = PropertyPhoto.property_photo_id WHERE :isThisTheIllustration = PropertyPhoto.isThisTheIllustration AND :propertyId = PropertyAndPropertyPhoto.propertyId")
    fun getPropertyIllustration(propertyId: Int, isThisTheIllustration: Boolean): LiveData<PropertyAndPropertyPhoto>

    @Query("SELECT * FROM PropertyAndPropertyPhoto INNER JOIN PropertyPhoto ON PropertyAndPropertyPhoto.propertyPhotoId = PropertyPhoto.property_photo_id WHERE :propertyId = propertyId")
    fun getPropertyPhotos(propertyId: Int): LiveData<List<PropertyAndPropertyPhoto>>

    @Insert
    fun insertPropertyPhoto(PropertyAndPropertyPhoto: PropertyAndPropertyPhoto)

    @Query("DELETE FROM PropertyAndPropertyPhoto WHERE :propertyId = propertyId AND :propertyPhotoId = propertyPhotoId")
    fun deletePropertyPhoto(propertyId: Int, propertyPhotoId: Int)

}