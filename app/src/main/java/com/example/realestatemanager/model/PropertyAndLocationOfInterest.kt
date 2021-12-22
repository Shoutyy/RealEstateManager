package com.example.realestatemanager.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = [ "propertyId", "locationOfInterestId" ])
class PropertyAndLocationOfInterest(
    val propertyId: Int,
    val locationOfInterestId: Int
)