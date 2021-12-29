package com.example.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PropertyPhoto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "property_photo_id")
    var id: Int = 0,
    val name: String,
    val wording: Wording,
    val isThisTheIllustration: Boolean
)