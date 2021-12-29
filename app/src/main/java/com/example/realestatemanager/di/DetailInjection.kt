package com.example.realestatemanager.di

import android.content.Context
import com.example.realestatemanager.model.PropertyAndLocationOfInterest
import com.example.realestatemanager.repository.*
import com.example.realestatemanager.database.AppDatabase
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DetailInjection {

    companion object {

        private fun providePropertyDataSource(context: Context): PropertyDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyDataRepository(database.propertyDao())
        }

        private fun providePropertyAndPropertyPhotoDataSource(context: Context): PropertyAndPropertyPhotoDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyAndPropertyPhotoDataRepository(database.propertyAndPropertyPhotoDao())
        }

        private fun providePropertyAndLocationOfInterestDataSource(context: Context): PropertyAndLocationOfInterestDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyAndLocationOfInterestDataRepository(database.propertyAndLocationOfInterestDao())
        }

        fun provideViewModelFactory(context: Context): DetailViewModelFactory {
            val dataSourceProperty = providePropertyDataSource(context)
            val dataSourcePropertyAndPropertyPhoto = providePropertyAndPropertyPhotoDataSource(context)
            val dateSourcePropertyAndLocationOfInterest = providePropertyAndLocationOfInterestDataSource(context)
            return DetailViewModelFactory(dataSourceProperty, dataSourcePropertyAndPropertyPhoto, dateSourcePropertyAndLocationOfInterest)
        }

    }
}