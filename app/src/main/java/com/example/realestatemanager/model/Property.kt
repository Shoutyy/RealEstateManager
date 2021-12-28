package com.example.realestatemanager.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
(foreignKeys = [ForeignKey(entity = Address::class,
    parentColumns = ["address_id"], childColumns = ["addressId"]), ForeignKey(entity = Agent::class,
    parentColumns = ["agent_id"], childColumns = ["agentId"])])

class Property(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val type: Type,
    val price: Long,
    val surface: Int,
    val rooms: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val description: String,
    val addressId: Int,
    val available: Boolean,
    val entryDate: Long,
    val saleDate: Long?,
    val agentId: Int
) {
    @Embedded var address: Address? = null
    @Embedded var agent: Agent? = null
}

