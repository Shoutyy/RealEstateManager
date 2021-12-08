package com.example.realestatemanager.database.converter

import androidx.room.TypeConverter
import com.example.realestatemanager.model.Type

class TypeConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromType(type: Type): Int {
            return type.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toType(int: Int): Type {
            return Type.values()[int]
        }
    }
}
