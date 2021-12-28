package com.example.realestatemanager.di

import android.content.Context
import com.example.realestatemanager.database.AppDatabase
import com.example.realestatemanager.repository.AgentDataRepository
import com.example.realestatemanager.repository.PropertyAndLocationOfInterestDataRepository
import com.example.realestatemanager.repository.PropertyAndPropertyPhotoDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository

class GetInjection {

    companion object {

        private fun providePropertyDataSource(context: Context): PropertyDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyDataRepository(database.propertyDao())
        }

        private fun provideAgentDataSource(context: Context): AgentDataRepository {
            val database = AppDatabase.getInstance(context)
            return AgentDataRepository(database.agentDao())
        }

        private fun providePropertyAndLocationOfInterestDataSource(context: Context): PropertyAndLocationOfInterestDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyAndLocationOfInterestDataRepository(database.propertyAndLocationOfInterestDao())
        }

        private fun providePropertyAndPropertyPhoto(context: Context): PropertyAndPropertyPhotoDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyAndPropertyPhotoDataRepository(database.propertyAndPropertyPhotoDao())
        }

        fun provideViewModelFactory(context: Context): GetViewModelFactory {
            val dataSourceProperty = providePropertyDataSource(context)
            val dataSourceAgent = provideAgentDataSource(context)
            val dataSourcePropertyAndLocationOfInterest = providePropertyAndLocationOfInterestDataSource(context)
            val dataSourcePropertyAndPropertyPhoto = providePropertyAndPropertyPhoto(context)
            return GetViewModelFactory(dataSourceProperty,
                dataSourceAgent,
                dataSourcePropertyAndLocationOfInterest,
                dataSourcePropertyAndPropertyPhoto)
        }

    }

}