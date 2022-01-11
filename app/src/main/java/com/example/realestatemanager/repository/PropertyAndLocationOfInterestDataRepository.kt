package com.example.realestatemanager.repository

import androidx.lifecycle.LiveData
import com.example.realestatemanager.database.dao.PropertyAndLocationOfInterestDao
import com.example.realestatemanager.model.PropertyAndLocationOfInterest

class PropertyAndLocationOfInterestDataRepository(private val propertyAndLocationOfInterestDao: PropertyAndLocationOfInterestDao) {

    fun getLocationsOfInterest(propertyId: Int): LiveData<List<PropertyAndLocationOfInterest>> = propertyAndLocationOfInterestDao.getLocationsOfInterest(propertyId)

    fun insertLocationOfInterest(propertyAndLocationOfInterest: PropertyAndLocationOfInterest) = propertyAndLocationOfInterestDao.insertLocationOfInterest(propertyAndLocationOfInterest)

    fun deleteLocationOfInterest(propertyId: Int, locationOfInterestId: Int) = propertyAndLocationOfInterestDao.deleteLocationOfInterest(propertyId, locationOfInterestId)

}