package com.example.realestatemanager.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.model.MapPropertyModelProcessed
import com.example.realestatemanager.model.Property
import com.example.realestatemanager.repository.PropertyDataRepository

class MapViewModel(propertyDataSource: PropertyDataRepository) : ViewModel() {

    private var _allProperties: LiveData<List<MapPropertyModelProcessed>> = Transformations.map(propertyDataSource.getAllProperties()) { it.map { property -> buildAddressModelProcessed(property) } }
    val allProperties: LiveData<List<MapPropertyModelProcessed>> = _allProperties

    private fun buildAddressModelProcessed(property: Property) =
        MapPropertyModelProcessed(
            propertyId = property.id,
            addressGeocoding = property.address?.path + ", " + property.address?.city
        )

}