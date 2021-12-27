package com.example.realestatemanager.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.realestatemanager.ui.form.UpdateFormViewModel
import com.example.realestatemanager.repository.*
import java.lang.IllegalArgumentException
import java.util.concurrent.Executor

class UpdateFormViewModelFactory(
    private val propertyDataSource: PropertyDataRepository,
    private val addressDataSource: AddressDataRepository,
    private val agentDataSource: AgentDataRepository,
    private val compositionPropertyAndLocationOfInterestDataSource: PropertyAndLocationOfInterestDataRepository,
    private val propertyPhotoDataRepository: PropertyPhotoDataRepository,
    private val compositionPropertyAndPropertyPhotoDataSource: PropertyAndPropertyPhotoDataRepository,
    private val executor: Executor): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateFormViewModel::class.java)) {
            return UpdateFormViewModel(
                propertyDataSource,
                addressDataSource,
                agentDataSource,
                compositionPropertyAndLocationOfInterestDataSource,
                propertyPhotoDataRepository,
                compositionPropertyAndPropertyPhotoDataSource,
                executor) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel Class")
    }

}