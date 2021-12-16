package com.example.realestatemanager.ui.property

import androidx.lifecycle.*
import com.example.realestatemanager.model.*
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import com.example.realestatemanager.repository.AgentDataRepository
import java.util.concurrent.Executor

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
            price = property.price
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

    //address
    fun getAddress(addressId: Int): LiveData<Address> { return addressDataSource.getAddress(addressId) }

    fun insertAddress(address: Address) = executor.execute { addressDataSource.insertAddress(address) }

    fun updateAddress(address: Address) = executor.execute { addressDataSource.updateAddress(address) }

    fun deleteAddresses(addressId: Int) = executor.execute { addressDataSource.deleteAddress(addressId) }

    //property
    fun getProperty(propertyId: Int): LiveData<Property> { return propertyDataSource.getProperty(propertyId) }

    fun insertProperty(property: Property) = executor.execute { propertyDataSource.insertProperty(property) }

    fun updateProperty(property: Property) = executor.execute { propertyDataSource.updateProperty(property) }

    fun deleteProperty(propertyId: Int) = executor.execute { propertyDataSource.deleteProperty(propertyId) }

}
