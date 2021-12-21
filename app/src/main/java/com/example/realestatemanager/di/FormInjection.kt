package com.example.realestatemanager.di

import android.content.Context
import com.example.realestatemanager.database.AppDatabase
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.AgentDataRepository
import com.example.realestatemanager.repository.PropertyAndLocationOfInterestDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class FormInjection {

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

        private fun provideCompositionPropertyAndLocationOfInterestDataSource(context: Context): PropertyAndLocationOfInterestDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyAndLocationOfInterestDataRepository(database.propertyAndLocationOfInterestDao())
        }

        private fun provideExecutor() : Executor {
            return Executors.newSingleThreadExecutor()
        }

        fun provideViewModelFactory(context: Context): FormViewModelFactory {
            val dataSourceProperty = providePropertyDataSource(context)
            val dataSourceAddress = provideAddressDataSource(context)
            val dataSourceAgent = provideAgentDataSource(context)
            val dataSourceCompositionPropertyAndLocationOfInterest = provideCompositionPropertyAndLocationOfInterestDataSource(context)
            val executor = provideExecutor()
            return FormViewModelFactory(dataSourceProperty, dataSourceAddress, dataSourceAgent, dataSourceCompositionPropertyAndLocationOfInterest, executor)
        }

    }

}
