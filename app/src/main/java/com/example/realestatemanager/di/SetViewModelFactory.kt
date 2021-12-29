package com.example.realestatemanager.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.realestatemanager.ui.form.SetUpdateFormViewModel
import com.example.realestatemanager.repository.*

class SetViewModelFactory(
    private val propertyDataSource: PropertyDataRepository,
    private val agentDataSource: AgentDataRepository,
    private val propertyAndLocationOfInterestDataSource: PropertyAndLocationOfInterestDataRepository,
    private val propertyAndPropertyPhotoDataSource: PropertyAndPropertyPhotoDataRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SetUpdateFormViewModel::class.java)) {
            return SetUpdateFormViewModel(
            propertyDataSource,
            agentDataSource,
            propertyAndLocationOfInterestDataSource,
            propertyAndPropertyPhotoDataSource) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel Class")
    }

}