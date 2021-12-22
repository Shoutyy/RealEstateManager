package com.example.realestatemanager.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = [ "propertyId" ],
    foreignKeys = [ForeignKey(entity = PropertyPhoto::class, parentColumns = ["property_photo_id"], childColumns = ["propertyPhotoId"])])

class PropertyAndPropertyPhoto (
    val propertyId: Int,
    val propertyPhotoId: Int,
    @Embedded var propertyPhoto: PropertyPhoto? = null
)