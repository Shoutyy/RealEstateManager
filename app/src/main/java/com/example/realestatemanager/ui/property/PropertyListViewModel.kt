package com.example.realestatemanager.ui.property

import androidx.lifecycle.*
import android.content.Context
import com.example.realestatemanager.util.Utils
import com.example.realestatemanager.model.*
import com.example.realestatemanager.repository.PropertyDataRepository
import com.example.realestatemanager.repository.PropertyPhotoDataRepository
import com.example.realestatemanager.repository.PropertyAndPropertyPhotoDataRepository

class PropertyListViewModel (
        propertyDataSource: PropertyDataRepository,
        private val propertyAndPropertyPhotoDataRepository: PropertyAndPropertyPhotoDataRepository) : ViewModel() {

    private var _properties: LiveData<List<PropertyModelProcessed>> = Transformations.map(propertyDataSource.getProperties()) { it.map { property -> buildPropertyModelProcessed(property) } }
    val properties: LiveData<List<PropertyModelProcessed>> = _properties

    fun getPropertyIllustration(propertyId: Int, context: Context): LiveData<IllustrationModelProcessed> =
        Transformations.map(propertyAndPropertyPhotoDataRepository.getPropertyIllustration(propertyId, true)) { getIllustrationModelProcessed(it, context) }

    //factory
    private fun buildPropertyModelProcessed(property: Property) =
        PropertyModelProcessed(
            propertyId = property.id,
            path = property.address?.path,
            type = Utils.fromTypeToString(property.type),
            district = Utils.fromDistrictToString(property.address?.district),
            price = Utils.fromPriceToString(property.price)
        )

    private fun getIllustrationModelProcessed(composition: PropertyAndPropertyPhoto, context: Context) =
        IllustrationModelProcessed(
            propertyId = composition.propertyId,
            illustration = Utils.getInternalBitmap(composition.propertyId.toString(), composition.propertyPhoto?.name, context)
        )
}
