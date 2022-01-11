package com.example.realestatemanager.database.dao

import androidx.room.*
import com.example.realestatemanager.model.Address

@Dao
interface AddressDao {

    @Insert
    fun insertAddress(address: Address): Long

    @Update
    fun updateAddress(address: Address)

}