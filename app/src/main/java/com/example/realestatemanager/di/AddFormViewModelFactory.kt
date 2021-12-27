package com.example.realestatemanager.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.realestatemanager.ui.form.AddFormViewModel
import com.example.realestatemanager.repository.*
import java.lang.IllegalArgumentException
import java.util.concurrent.Executor

class AddFormViewModelFactory(
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val propertyAndLocationOfInterestDataSource: PropertyAndLocationOfInterestDataRepository,
        private val propertyPhotoDataRepository: PropertyPhotoDataRepository,
        private val propertyAndPropertyPhotoDataSource: PropertyAndPropertyPhotoDataRepository,
        private val executor: Executor): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddFormViewModel::class.java)) {
            return AddFormViewModel(propertyDataSource,
                addressDataSource,
                agentDataSource,
                propertyAndLocationOfInterestDataSource,
                propertyPhotoDataRepository,
                propertyAndPropertyPhotoDataSource,
                executor) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel Class")
    }

}