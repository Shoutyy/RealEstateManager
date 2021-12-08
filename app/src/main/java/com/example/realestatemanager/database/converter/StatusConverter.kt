package com.example.realestatemanager.database.converter

import androidx.room.TypeConverter
import com.example.realestatemanager.model.Status

class StatusConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromStatus(status: Status): Int {
            return status.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toStatus(int: Int): Status {
            return Status.values()[int]
        }
    }
}
