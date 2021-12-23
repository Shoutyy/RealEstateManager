package com.example.realestatemanager.ui.media

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.model.PropertyAndPropertyPhoto
import com.example.realestatemanager.model.Wording
import com.example.realestatemanager.model.PhotoModelProcessed
import com.example.realestatemanager.repository.PropertyAndPropertyPhotoDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import java.util.concurrent.Executor

class MediaViewModel(
    private val propertyDataSource: PropertyDataRepository,
    private val compositionPropertyAndPropertyPhotoDataSource: PropertyAndPropertyPhotoDataRepository) : ViewModel() {

    fun getPropertyPhotos(propertyId: Int): LiveData<List<PhotoModelProcessed>> =
        Transformations.map(compositionPropertyAndPropertyPhotoDataSource.getPropertyPhotos(propertyId)) { it.map { propertyPhoto -> buildPhotoModelProcessed(propertyPhoto) } }

    fun getPropertyPath(propertyId: Int): LiveData<String> =
        Transformations.map(propertyDataSource.getProperty(propertyId)) { it.address?.path }

    private fun buildPhotoModelProcessed(propertyPhoto: PropertyAndPropertyPhoto) =
        PhotoModelProcessed(
            name = propertyPhoto.propertyPhoto?.name,
            wording = getWordingIntoStringForUi(propertyPhoto.propertyPhoto?.wording)
        )

    private fun getWordingIntoStringForUi(wording: Wording?) =
        when(wording) {
            Wording.STREET_VIEW -> "Street View"
            Wording.LIVING_ROOM -> "Living room"
            Wording.HALL -> "Hall"
            Wording.KITCHEN -> "Kitchen"
            Wording.DINING_ROOM -> "Dining room"
            Wording.BATHROOM -> "Bathroom"
            Wording.BALCONY -> "Balcony"
            Wording.BEDROOM -> "Bedroom"
            Wording.TERRACE -> "Terrace"
            Wording.WALK_IN_CLOSET -> "Walk in closet"
            Wording.OFFICE -> "Office"
            Wording.ROOF_TOP -> "Roof top"
            Wording.PLAN -> "plan"
            Wording.HALLWAY -> "Hallway"
            Wording.VIEW -> "View"
            Wording.GARAGE -> "Garage"
            Wording.SWIMMING_POOL -> "Swimming pool"
            Wording.FITNESS_CENTRE -> "Fitness centre"
            Wording.SPA -> "Spa"
            Wording.CINEMA -> "Cinema"
            Wording.CONFERENCE -> "Conference"
            Wording.STAIRS -> "Stairs"
            Wording.GARDEN -> "Garden"
            Wording.FLOOR -> "Floor"
            else -> null
        }
}