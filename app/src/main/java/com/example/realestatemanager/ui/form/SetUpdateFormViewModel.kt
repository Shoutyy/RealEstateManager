package com.example.realestatemanager.ui.form

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.util.Utils
import com.example.realestatemanager.model.FormPhotoAndWording
import com.example.realestatemanager.model.UpdateFormLocationsOfInterestModelProcessed
import com.example.realestatemanager.model.UpdateFormPropertyModelProcessed
import com.example.realestatemanager.model.PropertyAndLocationOfInterest
import com.example.realestatemanager.model.PropertyAndPropertyPhoto
import com.example.realestatemanager.model.Property
import com.example.realestatemanager.model.Wording
import com.example.realestatemanager.repository.*
import java.text.SimpleDateFormat
import java.util.*

class SetUpdateFormViewModel(
    private val propertyDataSource: PropertyDataRepository,
    agentDataSource: AgentDataRepository,
    private val propertyAndLocationOfInterestDataSource: PropertyAndLocationOfInterestDataRepository,
    private val propertyAndPropertyPhotoDataSource: PropertyAndPropertyPhotoDataRepository) : ViewModel() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    private var _fullNameAgents: LiveData<List<String>> = Transformations.map(agentDataSource.getAgents()) { list -> list.map { agent -> agent.firstName + " " + agent.name } }
    val fullNameAgents: LiveData<List<String>> = _fullNameAgents

    fun getProperty(propertyId: Int): LiveData<UpdateFormPropertyModelProcessed> {
        return Transformations.map(propertyDataSource.getProperty(propertyId)) { buildPropertyModelProcessed(it) }
    }

    fun getLocationsOfInterest(propertyId: Int): LiveData<UpdateFormLocationsOfInterestModelProcessed> {
        return Transformations.map(propertyAndLocationOfInterestDataSource.getLocationsOfInterest(propertyId)) { buildLocationOfInterest(it) }
    }

    fun getPropertyPhotos(propertyId: Int, path: String?, context: Context): LiveData<List<FormPhotoAndWording>> {
        return Transformations.map(propertyAndPropertyPhotoDataSource.getPropertyPhotos(propertyId)) {
            it.map {
                    composition -> buildFormPhotoAndWording(composition, path, context)
            }
        }
    }

    //---FACTORY---\\
    private fun buildPropertyModelProcessed(property: Property)
            = UpdateFormPropertyModelProcessed(
        type = Utils.getTypeIntoStringForUi(property.type),
        price = property.price.toString(),
        surface = property.surface.toString(),
        rooms = property.rooms.toString(),
        bedrooms = property.bedrooms.toString(),
        bathrooms = property.bathrooms.toString(),
        description = property.description,
        available = property.available,
        entryDate = getEntryDateIntoStringForUi(property.entryDate),
        saleDate = getSaleDateIntoStringForUi(property.saleDate),
        addressId = property.addressId,
        path = property.address?.path,
        complement = if (property.address?.complement != null) { property.address?.complement } else { "" },
        district = Utils.getDistrictIntoStringForUi(property.address?.district),
        city = Utils.getCityIntoStringForUi(property.address?.city),
        postalCode = property.address?.postalCode,
        country = Utils.getCountryIntoStringForUi(property.address?.country),
        fullNameAgent = Utils.getAgentIntoStringForUi(property.agentId)
    )

    private fun getEntryDateIntoStringForUi(entryDate: Long) = dateFormat.format(Date(entryDate))

    private fun getSaleDateIntoStringForUi(saleDate: Long?) =
        if (saleDate != null) {
            dateFormat.format(Date(saleDate))
        } else {
            ""
        }

    private fun buildLocationOfInterest(listComposition: List<PropertyAndLocationOfInterest>): UpdateFormLocationsOfInterestModelProcessed {
        var school = false
        var commerces= false
        var park = false
        var subways = false
        var train = false
        for(locationOfInterest in listComposition) {
            when(locationOfInterest.locationOfInterestId) {
                0 -> school = true
                1 -> commerces = true
                2 -> park = true
                3 -> subways = true
                4 -> train = true
            }
        }
        return UpdateFormLocationsOfInterestModelProcessed(
            school = school,
            commerces = commerces,
            park = park,
            subways = subways,
            train = train
        )
    }

    private fun buildFormPhotoAndWording(composition: PropertyAndPropertyPhoto, path: String?, context: Context) =
        FormPhotoAndWording(
            photo = Utils.getInternalBitmap(path, composition.propertyPhoto?.name, context),
            wording = getWordingIntoStringForUi(composition.propertyPhoto?.wording)
        )

    private fun getWordingIntoStringForUi(wording: Wording?) =
        when(wording) {
            Wording.STREET_VIEW -> "Street view"
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
            else -> "Unknown wording"
        }

}