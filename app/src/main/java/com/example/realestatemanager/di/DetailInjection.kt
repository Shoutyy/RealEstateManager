package com.example.realestatemanager.di

import android.content.Context
import com.example.realestatemanager.model.PropertyAndLocationOfInterest
import com.example.realestatemanager.repository.PropertyAndLocationOfInterestDataRepository
import com.example.realestatemanager.database.AppDatabase
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.AgentDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DetailInjection {

    companion object {

        private fun providePropertyDataSource(context: Context): PropertyDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyDataRepository(database.propertyDao())
        }

        private fun providePropertyAndLocationOfInterestDataSource(context: Context): PropertyAndLocationOfInterestDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyAndLocationOfInterestDataRepository(database.propertyAndLocationOfInterestDao())
        }

        fun provideViewModelFactory(context: Context): DetailViewModelFactory {
            val dataSourceProperty = providePropertyDataSource(context)
            val dateSourcePropertyAndLocationOfInterest = providePropertyAndLocationOfInterestDataSource(context)
            return DetailViewModelFactory(dataSourceProperty, dateSourcePropertyAndLocationOfInterest)
        }

    }
}