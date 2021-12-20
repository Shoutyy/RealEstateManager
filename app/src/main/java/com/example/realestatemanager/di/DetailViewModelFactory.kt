package com.example.realestatemanager.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.realestatemanager.ui.property.PropertyDetailViewModel
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.AgentDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import com.example.realestatemanager.repository.PropertyAndLocationOfInterestDataRepository
import java.lang.IllegalArgumentException
import java.util.concurrent.Executor

class DetailViewModelFactory (
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val propertyAndLocationOfInterestDataSource: PropertyAndLocationOfInterestDataRepository,
        private val executor: Executor): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyDetailViewModel::class.java)) {
            return PropertyDetailViewModel(propertyDataSource, addressDataSource, agentDataSource, propertyAndLocationOfInterestDataSource, executor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}
