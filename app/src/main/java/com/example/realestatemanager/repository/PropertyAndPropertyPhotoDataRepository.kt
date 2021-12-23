package com.example.realestatemanager.repository

import androidx.lifecycle.LiveData
import com.example.realestatemanager.database.dao.PropertyAndPropertyPhotoDao
import com.example.realestatemanager.model.PropertyAndPropertyPhoto

class PropertyAndPropertyPhotoDataRepository(private val propertyAndPropertyPhotoDao: PropertyAndPropertyPhotoDao) {

    fun getIllustrationPropertyPhotos(isThisTheIllustration: Boolean): LiveData<List<PropertyAndPropertyPhoto>> { return propertyAndPropertyPhotoDao.getIllustrationPropertyPhotos(isThisTheIllustration) }

    fun getPropertyPhotos(propertyId: Int): LiveData<List<PropertyAndPropertyPhoto>> { return propertyAndPropertyPhotoDao.getPropertyPhotos(propertyId) }

    fun insertPropertyPhoto(propertyAndPropertyPhoto: PropertyAndPropertyPhoto) = propertyAndPropertyPhotoDao.insertPropertyPhoto(propertyAndPropertyPhoto)

    fun updatePropertyPhoto(propertyAndPropertyPhoto: PropertyAndPropertyPhoto) = propertyAndPropertyPhotoDao.updatePropertyPhoto(propertyAndPropertyPhoto)

    fun deletePropertyPhoto(propertyId: Int) = propertyAndPropertyPhotoDao.deletePropertyPhoto(propertyId)

}