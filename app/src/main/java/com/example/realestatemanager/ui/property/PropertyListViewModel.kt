package com.example.realestatemanager.ui.property

import androidx.lifecycle.*
import com.example.realestatemanager.model.*
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import com.example.realestatemanager.repository.AgentDataRepository
import com.example.realestatemanager.repository.PropertyPhotoDataRepository
import com.example.realestatemanager.repository.PropertyAndPropertyPhotoDataRepository
import java.util.concurrent.Executor
import java.text.NumberFormat

class PropertyListViewModel (
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val propertyAndPropertyPhotoDataRepository: PropertyAndPropertyPhotoDataRepository,
        private val executor: Executor) : ViewModel() {

    private var _properties: LiveData<List<PropertyModelProcessed>> = Transformations.map(propertyDataSource.getProperties()) { it.map { property -> buildPropertyModelProcessed(property) } }
    val properties: LiveData<List<PropertyModelProcessed>> = _properties

    private var _illustrationsPropertiesPhotos: LiveData<List<IllustrationModelProcessed>> =
        Transformations.map(propertyAndPropertyPhotoDataRepository.getIllustrationPropertyPhotos(true)) {
            it.map { propertyAndPropertyPhoto -> buildUiModelForIllustration(propertyAndPropertyPhoto) }
        }

    val illustrationsPropertiesPhotos: LiveData<List<IllustrationModelProcessed>> = _illustrationsPropertiesPhotos

    private fun buildPropertyModelProcessed(property: Property) =
        PropertyModelProcessed(
            propertyId = property.id,
            path = property.address?.path,
            type = getTypeIntoStringForUi(property.type),
            district = getDistrictIntoStringForUi(property.address?.district),
            price = getPriceIntoStringForUi(property.price)
        )

    private fun getTypeIntoStringForUi(type: Type): String {
        return when(type) {
            Type.PENTHOUSE -> "Penthouse"
            Type.MANSION -> "Mansion"
            Type.FLAT -> "Flat"
            Type.DUPLEX -> "Duplex"
            Type.HOUSE -> "House"
            Type.LOFT -> "Loft"
            Type.TOWNHOUSE -> "Townhouse"
            Type.CONDO -> "Condo"
        }
    }

    private fun getDistrictIntoStringForUi(district: District?): String? {
        return when(district) {
            District.MANHATTAN -> "Manhattan"
            District.BROOKLYN -> "Brooklyn"
            District.STATEN_ISLAND -> "Staten Island"
            District.QUEENS -> "Queens"
            District.BRONX -> "Bronx"
            else -> null
        }
    }

    private fun getPriceIntoStringForUi(price: Long): String {
        return "$" + NumberFormat.getIntegerInstance().format(price)
    }

    private fun buildUiModelForIllustration(propertyAndPropertyPhoto: PropertyAndPropertyPhoto) =
        IllustrationModelProcessed(
            propertyId = propertyAndPropertyPhoto.propertyId,
            photoName = propertyAndPropertyPhoto.propertyPhoto?.name
        )
}
