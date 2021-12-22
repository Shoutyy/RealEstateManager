package com.example.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PropertyPhoto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "property_photo_id")
    val id: Int,
    val name: String,
    val wording: Int,
    val isThisTheIllustration: Boolean
)