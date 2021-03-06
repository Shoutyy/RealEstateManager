package com.example.realestatemanager.di

import android.content.Context
import com.example.realestatemanager.database.AppDatabase
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import com.example.realestatemanager.repository.AgentDataRepository
import com.example.realestatemanager.repository.PropertyAndPropertyPhotoDataRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ListInjection {

    companion object {

        private fun providePropertyDataSource(context: Context): PropertyDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyDataRepository(database.propertyDao())
        }

        private fun providePropertyAndPropertyPhotoDataSource(context: Context): PropertyAndPropertyPhotoDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyAndPropertyPhotoDataRepository(database.propertyAndPropertyPhotoDao())
        }

        fun provideViewModelFactory(context: Context): ListViewModelFactory {
            val dataSourceProperty = providePropertyDataSource(context)
            val dataSourcePropertyAndPropertyPhoto = providePropertyAndPropertyPhotoDataSource(context)
            return ListViewModelFactory(dataSourceProperty, dataSourcePropertyAndPropertyPhoto)
        }

    }
}