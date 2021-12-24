package com.example.realestatemanager.ui.property

import androidx.lifecycle.*
import com.example.realestatemanager.model.*
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.AgentDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import com.example.realestatemanager.repository.PropertyAndLocationOfInterestDataRepository
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

class PropertyDetailViewModel (
        private val propertyDataSource: PropertyDataRepository,
        private val propertyAndLocationOfInterestDataSource: PropertyAndLocationOfInterestDataRepository) : ViewModel() {

    private val dateFormat = SimpleDateFormat ("dd/MM/yyyy", Locale.getDefault())

    fun getProperty(propertyId: Int): LiveData<PropertyDetailModelProcessed> =
        Transformations.map(propertyDataSource.getProperty(propertyId)) { buildPropertyModelProcessed(it) }

    private fun buildPropertyModelProcessed(property: Property) =
        PropertyDetailModelProcessed(
            description = property.description,
            surface = surfaceIntToStringForUi(property.surface),
            rooms = property.rooms.toString(),
            bathrooms = property.bathrooms.toString(),
            bedrooms = property.bedrooms.toString(),
            available = property.available,
            path = property.address?.path,
            complement = property.address?.complement,
            city = getCityIntoStringForUi(property.address?.city),
            postalCode = property.address?.postalCode,
            country = getCountryIntoStringForUi(property.address?.country),
            agentFullName = getAgentFullName(property.agent?.firstName, property.agent?.name),
            entryDate = getEntryDateIntoStringForUi(property.entryDate),
            saleDate = getSaleDateIntoStringForUi(property.saleDate)
        )

    //TODO SUPPRESS RETURN VALUE
    private fun surfaceIntToStringForUi(surface: Int?) = surface.toString() + "sq ft"

    private fun getCityIntoStringForUi(city: City?) =
        when(city) {
            City.NEW_YORK -> "New York"
            else -> null
        }

    private fun getCountryIntoStringForUi(country: Country?) =
        when(country) {
            Country.UNITED_STATES -> "United States"
            else -> null
        }


    private fun getAgentFullName(firstName: String?, name: String?) = "$firstName $name"

    private fun getEntryDateIntoStringForUi(entryDate: Long) = dateFormat.format(Date(entryDate))

    private fun getSaleDateIntoStringForUi(saleDate: Long?) =
        if (saleDate != null) {
            dateFormat.format(Date(saleDate))
        } else {
            null
        }

    fun getLocationsOfInterest(propertyId: Int): LiveData<LocationOfInterestModelProcessed> =
        Transformations.map(propertyAndLocationOfInterestDataSource.getLocationsOfInterest(propertyId)) { buildLocationOfInterestModelProcessed(it) }

    private fun buildLocationOfInterestModelProcessed(composition: List<PropertyAndLocationOfInterest>): LocationOfInterestModelProcessed {
        var school = false
        var commerces= false
        var park = false
        var subways = false
        var train = false
        for(locationOfInterest in composition) {
            when(locationOfInterest.locationOfInterestId) {
                0 -> school = true
                1 -> commerces = true
                2 -> park = true
                3 -> subways = true
                4 -> train = true
            }
        }
        return LocationOfInterestModelProcessed(
            school = school,
            commerces = commerces,
            park = park,
            subways = subways,
            train = train
        )
    }
}


