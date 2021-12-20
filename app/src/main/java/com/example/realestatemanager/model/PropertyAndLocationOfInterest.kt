package com.example.realestatemanager.model

import androidx.room.Entity

@Entity(primaryKeys = [ "propertyId", "locationOfInterestId" ])
class PropertyAndLocationOfInterest(
    val propertyId: Int,
    val locationOfInterestId: Int
)