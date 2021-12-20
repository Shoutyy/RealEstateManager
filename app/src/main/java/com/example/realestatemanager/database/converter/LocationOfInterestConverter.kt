package com.example.realestatemanager.database.converter

import androidx.room.TypeConverter
import com.example.realestatemanager.model.LocationOfInterest

class LocationOfInterestConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromLocationOfInterest(locationsOfInterest: LocationOfInterest): Int {
            return locationsOfInterest.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toLocationOfInterest(int: Int): LocationOfInterest {
            return LocationOfInterest.values()[int]
        }
    }
}