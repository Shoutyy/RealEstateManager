package com.example.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity
class Address(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "address_id")
    var id: Int = 0,
    val path: String,
    val complement: String?,
    val district: District,
    val city: City,
    val postalCode: String,
    val country: Country
)