package com.example.realestatemanager.database.converter

import androidx.room.TypeConverter
import com.example.realestatemanager.model.Country

class CountryConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromCountry(country: Country): Int {
            return country.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toCountry(int: Int): Country {
            return Country.values()[int]
        }
    }
}