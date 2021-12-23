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

        private fun provideAddressDataSource(context: Context): AddressDataRepository {
            val database = AppDatabase.getInstance(context)
            return AddressDataRepository(database.addressDao())
        }

        private fun provideAgentDataSource(context: Context): AgentDataRepository {
            val database = AppDatabase.getInstance(context)
            return AgentDataRepository(database.agentDao())
        }

        private fun providePropertyAndPropertyPhotoDataSource(context: Context): PropertyAndPropertyPhotoDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyAndPropertyPhotoDataRepository(database.propertyAndPropertyPhotoDao())
        }

        private fun provideExecutor(): Executor {
            return Executors.newSingleThreadExecutor()
        }

        fun provideViewModelFactory(context: Context): ListViewModelFactory {
            val dataSourceProperty = providePropertyDataSource(context)
            val dataSourceAddress = provideAddressDataSource(context)
            val dataSourceAgent = provideAgentDataSource(context)
            val dataSourcePropertyAndPropertyPhoto = providePropertyAndPropertyPhotoDataSource(context)
            val executor = provideExecutor()
            return ListViewModelFactory(dataSourceProperty, dataSourceAddress, dataSourceAgent, dataSourcePropertyAndPropertyPhoto,  executor)
        }

    }
}