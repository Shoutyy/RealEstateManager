package com.example.realestatemanager.repository

import com.example.realestatemanager.database.dao.AddressDao
import com.example.realestatemanager.model.Address

class AddressDataRepository(private val addressDao: AddressDao) {

    fun insertAddress(address: Address): Long = addressDao.insertAddress(address)

    fun updateAddress(address: Address) = addressDao.updateAddress(address)

}