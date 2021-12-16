package com.example.realestatemanager.ui.property

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.model.*
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.AgentDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import java.util.concurrent.Executor

class PropertyDetailViewModel (
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val executor: Executor) : ViewModel() {

    fun getFirstProperty(): LiveData<ModelsProcessedPropertyDetail> {
        return Transformations.map(propertyDataSource.getFirstProperty()) { buildUiModel(it) }
    }

    fun getProperty(propertyId: Int): LiveData<ModelsProcessedPropertyDetail> {
        return Transformations.map(propertyDataSource.getProperty(propertyId)) { buildUiModel(it) }
    }

    private fun buildUiModel(property: Property) =
        ModelsProcessedPropertyDetail(
            description = property.description,
            surface = surfaceIntToString(property.surface),
            rooms = property.rooms.toString(),
            bathrooms = property.bathrooms.toString(),
            bedrooms = property.bedrooms.toString(),
            path = property.address?.path,
            complement = property.address?.complement,
            city = getCityIntoStringForUi(property.address?.city),
            postalCode = property.address?.postalCode,
            country = getCountryIntoStringForUi(property.address?.country)
        )

    private fun surfaceIntToString(surface: Int?): String {
        return surface.toString() + "sq ft"
    }

    private fun getCityIntoStringForUi(city: City?): String? {
        return when(city) {
            City.NEW_YORK -> "New York"
            else -> null
        }
    }

    private fun getCountryIntoStringForUi(country: Country?): String? {
        return when(country) {
            Country.UNITED_STATES -> "United States"
            else -> null
        }
    }
}


