package com.example.realestatemanager.ui.property

import androidx.lifecycle.*
import android.content.Context
import android.graphics.Bitmap
import com.example.realestatemanager.util.Utils
import com.example.realestatemanager.model.*
import com.example.realestatemanager.repository.PropertyDataRepository
import com.example.realestatemanager.repository.PropertyPhotoDataRepository
import com.example.realestatemanager.repository.PropertyAndPropertyPhotoDataRepository
import java.text.NumberFormat

class PropertyListViewModel (
        propertyDataSource: PropertyDataRepository,
        private val propertyAndPropertyPhotoDataRepository: PropertyAndPropertyPhotoDataRepository) : ViewModel() {

    private var _properties: LiveData<List<PropertyModelProcessed>> = Transformations.map(propertyDataSource.getProperties()) { it.map { property -> buildPropertyModelProcessed(property) } }
    val properties: LiveData<List<PropertyModelProcessed>> = _properties

    fun getPropertyIllustration(propertyId: Int, path: String?, context: Context): LiveData<IllustrationModelProcessed> =
        Transformations.map(propertyAndPropertyPhotoDataRepository.getPropertyIllustration(propertyId, true)) { getIllustrationModelProcessed(it, path, context) }

    //factory
    private fun buildPropertyModelProcessed(property: Property) =
        PropertyModelProcessed(
            propertyId = property.id,
            path = property.address?.path,
            type = getTypeIntoStringForUi(property.type),
            district = getDistrictIntoStringForUi(property.address?.district),
            price = getPriceIntoStringForUi(property.price)
        )

    private fun getTypeIntoStringForUi(type: Type) =
        when(type) {
            Type.PENTHOUSE -> "Penthouse"
            Type.MANSION -> "Mansion"
            Type.FLAT -> "Flat"
            Type.DUPLEX -> "Duplex"
            Type.HOUSE -> "House"
            Type.LOFT -> "Loft"
            Type.TOWNHOUSE -> "Townhouse"
            Type.CONDO -> "Condo"
        }

    private fun getDistrictIntoStringForUi(district: District?) =
        when(district) {
            District.MANHATTAN -> "Manhattan"
            District.BROOKLYN -> "Brooklyn"
            District.STATEN_ISLAND -> "Staten Island"
            District.QUEENS -> "Queens"
            District.BRONX -> "Bronx"
            else -> "District unknown"
        }

    private fun getPriceIntoStringForUi(price: Long) = "$" + NumberFormat.getIntegerInstance().format(price)

    private fun getIllustrationModelProcessed(composition: PropertyAndPropertyPhoto, path: String?, context: Context) =
        IllustrationModelProcessed(
            propertyId = composition.propertyId,
            illustration = Utils.getInternalBitmap(path, composition.propertyPhoto?.name, context)
        )
}
