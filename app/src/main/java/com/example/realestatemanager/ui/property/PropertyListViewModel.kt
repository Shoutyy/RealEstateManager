package com.example.realestatemanager.ui.property

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.model.Address
import com.example.realestatemanager.model.Property
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import java.util.concurrent.Executor

class PropertyListViewModel (
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val executor: Executor) : ViewModel() {

    private var currentProperties: LiveData<List<Property>>? = null
    private var currentAddresses: LiveData<List<Address>>? = null

    fun init() {
        if (this.currentProperties != null && this.currentAddresses != null) {
            return
        }
        currentProperties = propertyDataSource.getProperties()
        currentAddresses = addressDataSource.getAddresses()
    }

    //address
    fun getAddresses(): LiveData<List<Address>> {return addressDataSource.getAddresses()}

    fun getAddress(addressId: Int): LiveData<Address> {return addressDataSource.getAddress(addressId)}

    fun insertAddress(address: Address) = executor.execute {addressDataSource.insertAddress(address)}

    fun updateAddress(address: Address) = executor.execute {addressDataSource.updateAddress(address)}

    fun deleteAddresses(addressId: Int) = executor.execute {addressDataSource.deleteAddress(addressId)}

    //property
    fun getProperties(): LiveData<List<Property>> { return propertyDataSource.getProperties() }

    fun getProperty(propertyId: Int): LiveData<Property> { return propertyDataSource.getProperty(propertyId)}

    fun insertProperty(property: Property) = executor.execute { propertyDataSource.insertProperty(property) }

    fun updateProperty(property: Property) = executor.execute { propertyDataSource.updateProperty(property) }

    fun deleteProperty(propertyId: Int) = executor.execute { propertyDataSource.deleteProperty(propertyId) }
}
