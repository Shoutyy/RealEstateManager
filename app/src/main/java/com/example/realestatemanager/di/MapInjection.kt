package com.example.realestatemanager.di

import android.content.Context
import com.example.realestatemanager.database.AppDatabase
import com.example.realestatemanager.repository.PropertyDataRepository

class MapInjection {

    companion object {

        private fun providePropertyDataSource(context: Context): PropertyDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyDataRepository(database.propertyDao())
        }

        fun provideViewModelFactory(context: Context): MapViewModelFactory {
            val dataSourceProperty = providePropertyDataSource(context)
            return MapViewModelFactory(dataSourceProperty)
        }

    }

}
