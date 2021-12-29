package com.example.realestatemanager.ui.property

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.model.*
import com.example.realestatemanager.util.Utils
import com.example.realestatemanager.repository.PropertyDataRepository
import com.example.realestatemanager.repository.PropertyAndLocationOfInterestDataRepository
import com.example.realestatemanager.repository.PropertyAndPropertyPhotoDataRepository

class PropertyDetailViewModel (
        private val propertyDataSource: PropertyDataRepository,
        private val propertyAndPropertyPhotoDataSource: PropertyAndPropertyPhotoDataRepository,
        private val propertyAndLocationOfInterestDataSource: PropertyAndLocationOfInterestDataRepository) : ViewModel() {


    fun getProperty(propertyId: Int): LiveData<PropertyDetailModelProcessed> =
        Transformations.map(propertyDataSource.getProperty(propertyId)) { buildPropertyModelProcessed(it) }

    fun getPropertyPhotos(propertyId: Int, path: String?, context: Context): LiveData<List<PhotoModelProcessed>> =
        Transformations.map(propertyAndPropertyPhotoDataSource.getPropertyPhotos(propertyId)) { it.map { propertyPhoto -> buildPhotoModelProcessed(propertyPhoto, path, context) } }

    fun getLocationsOfInterest(propertyId: Int): LiveData<LocationsOfInterestModelProcessed> =
        Transformations.map(propertyAndLocationOfInterestDataSource.getLocationsOfInterest(propertyId)) { buildLocationOfInterestModelProcessed(it) }


    //factory
    private fun buildPropertyModelProcessed(property: Property) =
        PropertyDetailModelProcessed(
            description = property.description,
            surface = Utils.fromSurfaceToString(property.surface),
            rooms = property.rooms.toString(),
            bathrooms = property.bathrooms.toString(),
            bedrooms = property.bedrooms.toString(),
            available = property.available,
            path = property.address?.path,
            complement = property.address?.complement,
            city = Utils.fromCityToString(property.address?.city),
            postalCode = property.address?.postalCode,
            country = Utils.fromCountryToString(property.address?.country),
            agentFullName = Utils.fromAgentToString(property.agent?.firstName, property.agent?.name),
            entryDate = Utils.fromEntryDateToString(property.entryDate),
            saleDate = Utils.fromSaleDateToString(property.saleDate)
        )

    private fun buildPhotoModelProcessed(propertyPhoto: PropertyAndPropertyPhoto, path: String?, context: Context?) =
        PhotoModelProcessed(
            photo = Utils.getInternalBitmap(path, propertyPhoto.propertyPhoto?.name, context),
            wording = Utils.fromWordingToString(propertyPhoto.propertyPhoto?.wording)
        )

    private fun buildLocationOfInterestModelProcessed(composition: List<PropertyAndLocationOfInterest>): LocationsOfInterestModelProcessed {
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
        return LocationsOfInterestModelProcessed(
            school = school,
            commerces = commerces,
            park = park,
            subways = subways,
            train = train
        )
    }
}


