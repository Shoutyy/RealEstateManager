package com.example.realestatemanager.model

import android.content.ContentValues
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = [ "propertyId", "propertyPhotoId" ],
    foreignKeys = [ForeignKey(entity = PropertyPhoto::class, parentColumns = ["property_photo_id"], childColumns = ["propertyPhotoId"])])

class PropertyAndPropertyPhoto (
    val propertyId: Int,
    val propertyPhotoId: Int
) {
    @Embedded var propertyPhoto: PropertyPhoto? = null
    companion object {
        fun fromContentValues(values: ContentValues): PropertyAndPropertyPhoto =
            PropertyAndPropertyPhoto(
                propertyId = values.getAsInteger("propertyId"),
                propertyPhotoId = values.getAsInteger("propertyPhotoId")
            )
    }
}