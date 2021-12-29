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
import com.example.realestatemanager.repository.*
import java.text.SimpleDateFormat
import java.util.*

class SetUpdateFormViewModel(
    private val propertyDataSource: PropertyDataRepository,
    agentDataSource: AgentDataRepository,
    private val propertyAndLocationOfInterestDataSource: PropertyAndLocationOfInterestDataRepository,
    private val propertyAndPropertyPhotoDataSource: PropertyAndPropertyPhotoDataRepository) : ViewModel() {

    private var _fullNameAgents: LiveData<List<String>> = Transformations.map(agentDataSource.getAgents()) { list -> list.map { agent -> agent.firstName + " " + agent.name } }
    val fullNameAgents: LiveData<List<String>> = _fullNameAgents

    fun getProperty(propertyId: Int): LiveData<UpdateFormPropertyModelProcessed> {
        return Transformations.map(propertyDataSource.getProperty(propertyId)) { buildPropertyModelProcessed(it) }
    }

    fun getLocationsOfInterest(propertyId: Int): LiveData<UpdateFormLocationsOfInterestModelProcessed> {
        return Transformations.map(propertyAndLocationOfInterestDataSource.getLocationsOfInterest(propertyId)) { buildLocationOfInterest(it) }
    }

    fun getPropertyPhotos(propertyId: Int, context: Context): LiveData<List<FormPhotoAndWording>> {
        return Transformations.map(propertyAndPropertyPhotoDataSource.getPropertyPhotos(propertyId)) {
            it.map {
                    composition -> buildFormPhotoAndWording(composition, context)
            }
        }
    }

    //---FACTORY---\\
    private fun buildPropertyModelProcessed(property: Property)
            = UpdateFormPropertyModelProcessed(
        type = Utils.fromTypeToString(property.type),
        price = property.price.toString(),
        surface = property.surface.toString(),
        rooms = property.rooms.toString(),
        bedrooms = property.bedrooms.toString(),
        bathrooms = property.bathrooms.toString(),
        description = property.description,
        available = property.available,
        entryDate = Utils.fromEntryDateToString(property.entryDate),
        entryDateLong = property.entryDate,
        saleDate = Utils.fromSaleDateToString(property.saleDate),
        saleDateLong = property.saleDate ?: 0,
        addressId = property.addressId,
        path = property.address?.path,
        complement = if (property.address?.complement != null) { property.address?.complement } else { "" },
        district = Utils.fromDistrictToString(property.address?.district),
        city = Utils.fromCityToString(property.address?.city),
        postalCode = property.address?.postalCode,
        country = Utils.fromCountryToString(property.address?.country),
        fullNameAgent = Utils.fromAgentIdToString(property.agentId)
    )

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

    private fun buildFormPhotoAndWording(composition: PropertyAndPropertyPhoto, context: Context) =
        FormPhotoAndWording(
            id = composition.propertyPhoto?.id,
            name = composition.propertyPhoto?.name,
            photo = Utils.getInternalBitmap(composition.propertyId.toString(), composition.propertyPhoto?.name, context),
            wording = Utils.fromWordingToString(composition.propertyPhoto?.wording)
        )
}