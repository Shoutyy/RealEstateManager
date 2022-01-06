package com.example.realestatemanager.di

import android.content.Context
import com.example.realestatemanager.database.AppDatabase
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.PropertyAndLocationOfInterestDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository

class SearchFormInjection {

    companion object {

        private fun providePropertyDataSource(context: Context): PropertyDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyDataRepository(database.propertyDao())
        }

        fun provideViewModelFactory(context: Context): SearchFormViewModelFactory {
            val dataSourceProperty = providePropertyDataSource(context)
            return SearchFormViewModelFactory(dataSourceProperty)
        }
    }

}
