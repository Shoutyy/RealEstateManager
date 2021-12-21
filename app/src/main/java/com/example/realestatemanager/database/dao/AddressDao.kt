package com.example.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.realestatemanager.model.Address

@Dao
interface AddressDao {

    @Query("SELECT * FROM Address")
    fun getAddresses(): LiveData<List<Address>>

    @Query("SELECT * FROM Address WHERE :id == address_id")
    fun getAddress(id: Int): LiveData<Address>

    @Insert
    fun insertAddress(address: Address): Long

    @Update
    fun updateAddress(address: Address)

    @Query("DELETE FROM Address WHERE :id == address_id")
    fun deleteAddress(id: Int)

}