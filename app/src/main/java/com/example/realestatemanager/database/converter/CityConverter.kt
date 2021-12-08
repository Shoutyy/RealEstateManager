package com.example.realestatemanager.database.converter

import androidx.room.TypeConverter
import com.example.realestatemanager.model.City

class CityConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromCity(city: City): Int {
            return city.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toCity(int: Int): City {
            return City.values()[int]
        }
    }
}