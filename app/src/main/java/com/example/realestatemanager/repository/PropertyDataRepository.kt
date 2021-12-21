package com.example.realestatemanager.repository

import androidx.lifecycle.LiveData
import com.example.realestatemanager.database.dao.PropertyDao
import com.example.realestatemanager.model.Property

class PropertyDataRepository(private val propertyDao: PropertyDao) {

    fun getProperties(): LiveData<List<Property>> { return propertyDao.getProperties() }

    fun getProperty(propertyId: Int): LiveData<Property> { return propertyDao.getProperty(propertyId) }

    fun insertProperty(property: Property): Long { return propertyDao.insertProperty(property) }

    fun updateProperty(property: Property) = propertyDao.updateProperty(property)

    fun deleteProperty(propertyId: Int) = propertyDao.deleteProperty(propertyId)

}