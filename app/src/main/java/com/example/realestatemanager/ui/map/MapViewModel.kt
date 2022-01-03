package com.example.realestatemanager.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.model.MapPropertyModelProcessed
import com.example.realestatemanager.model.Property
import com.example.realestatemanager.repository.PropertyDataRepository

class MapViewModel(propertyDataSource: PropertyDataRepository) : ViewModel() {

    private var _properties: LiveData<List<MapPropertyModelProcessed>> = Transformations.map(propertyDataSource.getProperties()) { it.map { property -> buildAddressModelProcessed(property) } }
    val properties: LiveData<List<MapPropertyModelProcessed>> = _properties

    private fun buildAddressModelProcessed(property: Property) =
        MapPropertyModelProcessed(
            propertyId = property.id,
            addressGeocoding = property.address?.path + ", " + property.address?.city
        )

}