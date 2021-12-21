package com.example.realestatemanager.repository

import androidx.lifecycle.LiveData
import com.example.realestatemanager.database.dao.AddressDao
import com.example.realestatemanager.model.Address

class AddressDataRepository(private val addressDao: AddressDao) {

    fun getAddresses(): LiveData<List<Address>> { return addressDao.getAddresses() }

    fun getAddress(addressId: Int): LiveData<Address> { return this.addressDao.getAddress(addressId) }

    //fun insertAddress(address: Address) = addressDao.insertAddress(address)
    fun insertAddress(address: Address): Long { return addressDao.insertAddress(address) }

    fun updateAddress(address: Address) = addressDao.updateAddress(address)

    fun deleteAddress(addressId: Int) = addressDao.deleteAddress(addressId)

}