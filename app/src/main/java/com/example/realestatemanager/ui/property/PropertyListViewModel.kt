package com.example.realestatemanager.ui.property

import androidx.lifecycle.*
import com.example.realestatemanager.model.*
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import com.example.realestatemanager.repository.AgentDataRepository
import java.util.concurrent.Executor
import java.text.NumberFormat

class PropertyListViewModel (
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val executor: Executor) : ViewModel() {

    private var _properties: LiveData<List<ModelsProcessedPropertyList>> = Transformations.map(propertyDataSource.getProperties()) { it.map { property -> buildUiModel(property) } }
    val properties: LiveData<List<ModelsProcessedPropertyList>> = _properties

    private fun buildUiModel(property: Property) =
        ModelsProcessedPropertyList(
            propertyId = property.id,
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

}
