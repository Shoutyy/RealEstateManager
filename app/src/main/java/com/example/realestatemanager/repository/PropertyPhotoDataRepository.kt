package com.example.realestatemanager.repository

import androidx.lifecycle.LiveData
import com.example.realestatemanager.database.dao.PropertyPhotoDao
import com.example.realestatemanager.model.PropertyPhoto

class PropertyPhotoDataRepository(private val propertyPhotoDao: PropertyPhotoDao) {

    fun getIllustrationPropertyPhoto(propertyPhotoId: Int, isThisTheIllustration: Boolean): LiveData<PropertyPhoto> = propertyPhotoDao.getIllustrationPropertyPhoto(propertyPhotoId, isThisTheIllustration)

    fun insertPropertyPhoto(propertyPhoto: PropertyPhoto): Long = propertyPhotoDao.insertPropertyPhoto(propertyPhoto)

    fun updatePropertyPhoto(propertyPhoto: PropertyPhoto) = propertyPhotoDao.updatePropertyPhoto(propertyPhoto)

    fun deletePropertyPhoto(propertyPhotoId: Int) = propertyPhotoDao.deletePropertyPhoto(propertyPhotoId)

}