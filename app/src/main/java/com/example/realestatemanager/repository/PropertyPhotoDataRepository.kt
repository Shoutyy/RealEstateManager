package com.example.realestatemanager.repository
import com.example.realestatemanager.database.dao.PropertyPhotoDao
import com.example.realestatemanager.model.PropertyPhoto

class PropertyPhotoDataRepository(private val propertyPhotoDao: PropertyPhotoDao) {

    fun insertPropertyPhoto(propertyPhoto: PropertyPhoto): Long = propertyPhotoDao.insertPropertyPhoto(propertyPhoto)

    fun updatePropertyPhoto(propertyPhoto: PropertyPhoto) = propertyPhotoDao.updatePropertyPhoto(propertyPhoto)

    fun deletePropertyPhoto(propertyPhotoId: Int) = propertyPhotoDao.deletePropertyPhoto(propertyPhotoId)

}