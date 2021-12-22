package com.example.realestatemanager.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.realestatemanager.ui.property.PropertyListViewModel
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import com.example.realestatemanager.repository.AgentDataRepository
import com.example.realestatemanager.repository.PropertyPhotoDataRepository
import java.lang.IllegalArgumentException
import java.util.concurrent.Executor

class ListViewModelFactory (
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val propertyPhotoDataRepository: PropertyPhotoDataRepository,
        private val executor: Executor): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyListViewModel::class.java)) {
            return PropertyListViewModel(propertyDataSource, addressDataSource, agentDataSource, propertyPhotoDataRepository, executor) as T
        }
        throw IllegalArgumentException("ViewModel class Unknown ")
    }

}
