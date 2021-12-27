package com.example.realestatemanager.di


import android.content.Context
import com.example.realestatemanager.database.AppDatabase
import com.example.realestatemanager.repository.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class UpdateFormInjection {

    companion object {

        private fun providePropertyDataSource(context: Context): PropertyDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyDataRepository(database.propertyDao())
        }

        private fun provideAddressDataSource(context: Context): AddressDataRepository {
            val database = AppDatabase.getInstance(context)
            return AddressDataRepository(database.addressDao())
        }

        private fun provideAgentDataSource(context: Context): AgentDataRepository {
            val database = AppDatabase.getInstance(context)
            return AgentDataRepository(database.agentDao())
        }

        private fun providePropertyAndLocationOfInterestDataSource(context: Context): PropertyAndLocationOfInterestDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyAndLocationOfInterestDataRepository(database.propertyAndLocationOfInterestDao())
        }

        private fun providePropertyPhotoDataSource(context: Context): PropertyPhotoDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyPhotoDataRepository(database.propertyPhotoDao())
        }

        private fun providePropertyAndPropertyPhoto(context: Context): PropertyAndPropertyPhotoDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyAndPropertyPhotoDataRepository(database.propertyAndPropertyPhotoDao())
        }

        private fun provideExecutor() : Executor {
            return Executors.newSingleThreadExecutor()
        }

        fun provideViewModelFactory(context: Context): UpdateFormViewModelFactory {
            val dataSourceProperty = providePropertyDataSource(context)
            val dataSourceAddress = provideAddressDataSource(context)
            val dataSourceAgent = provideAgentDataSource(context)
            val dataSourceCompositionPropertyAndLocationOfInterest = providePropertyAndLocationOfInterestDataSource(context)
            val dataSourcePropertyPhoto = providePropertyPhotoDataSource(context)
            val dataSourceCompositionPropertyAndPropertyPhoto = providePropertyAndPropertyPhoto(context)
            val executor = provideExecutor()
            return UpdateFormViewModelFactory(dataSourceProperty,
                dataSourceAddress,
                dataSourceAgent,
                dataSourceCompositionPropertyAndLocationOfInterest,
                dataSourcePropertyPhoto,
                dataSourceCompositionPropertyAndPropertyPhoto,
                executor)
        }

    }

}
