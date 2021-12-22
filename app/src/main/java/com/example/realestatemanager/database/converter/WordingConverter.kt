package com.example.realestatemanager.database.converter

import androidx.room.TypeConverter
import com.example.realestatemanager.model.Wording

class WordingConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromWording(wording: Wording): Int {
            return wording.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toWording(int: Int): Wording {
            return Wording.values()[int]
        }
    }
}