package com.example.realestatemanager.model

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = [ "propertyId", "locationOfInterestId" ])
class PropertyAndLocationOfInterest(
    val propertyId: Int,
    val locationOfInterestId: Int
) {
    companion object {
        fun fromContentValues(values: ContentValues): PropertyAndLocationOfInterest =
            PropertyAndLocationOfInterest(
                propertyId = values.getAsInteger("propertyId"),
                locationOfInterestId = values.getAsInteger("locationOfInterestId")
            )
    }
}