package com.example.realestatemanager.repository

import androidx.lifecycle.LiveData
import com.example.realestatemanager.database.dao.PropertyAndPropertyPhotoDao
import com.example.realestatemanager.model.PropertyAndPropertyPhoto

class PropertyAndPropertyPhotoDataRepository(private val propertyAndPropertyPhotoDao: PropertyAndPropertyPhotoDao) {

    fun getPropertyIllustration(propertyId: Int, isThisTheIllustration: Boolean): LiveData<PropertyAndPropertyPhoto> = propertyAndPropertyPhotoDao.getPropertyIllustration(propertyId, isThisTheIllustration)

    fun getPropertyPhotos(propertyId: Int): LiveData<List<PropertyAndPropertyPhoto>> = propertyAndPropertyPhotoDao.getPropertyPhotos(propertyId)

    fun insertPropertyPhoto(propertyAndPropertyPhoto: PropertyAndPropertyPhoto) = propertyAndPropertyPhotoDao.insertPropertyPhoto(propertyAndPropertyPhoto)

    fun deletePropertyPhoto(propertyId: Int, propertyPhotoId: Int) = propertyAndPropertyPhotoDao.deletePropertyPhoto(propertyId, propertyPhotoId)

}