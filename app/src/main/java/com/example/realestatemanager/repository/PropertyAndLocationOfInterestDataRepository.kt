package com.example.realestatemanager.repository

import androidx.lifecycle.LiveData
import com.example.realestatemanager.database.dao.PropertyAndLocationOfInterestDao
import com.example.realestatemanager.model.PropertyAndLocationOfInterest

class PropertyAndLocationOfInterestDataRepository(private val propertyAndLocationOfInterestDao: PropertyAndLocationOfInterestDao) {

    fun getLocationsOfInterest(propertyId: Int): LiveData<List<PropertyAndLocationOfInterest>> { return propertyAndLocationOfInterestDao.getLocationsOfInterest(propertyId) }

    fun insertLocationOfInterest(propertyAndLocationOfInterest: PropertyAndLocationOfInterest) = propertyAndLocationOfInterestDao.insertLocationOfInterest(propertyAndLocationOfInterest)

    fun updateLocationOfInterest(propertyAndLocationOfInterest: PropertyAndLocationOfInterest) = propertyAndLocationOfInterestDao.updateLocationOfInterest(propertyAndLocationOfInterest)

    fun deleteLocationOfInterest(propertyId: Int) = propertyAndLocationOfInterestDao.deleteLocationOfInterest(propertyId)

}