package com.example.realestatemanager.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.realestatemanager.ui.property.PropertyDetailViewModel
import com.example.realestatemanager.repository.*
import java.lang.IllegalArgumentException
import java.util.concurrent.Executor

class DetailViewModelFactory (
        private val propertyDataSource: PropertyDataRepository,
        private val propertyAndPropertyPhotoDataSource: PropertyAndPropertyPhotoDataRepository,
        private val propertyAndLocationOfInterestDataSource: PropertyAndLocationOfInterestDataRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyDetailViewModel::class.java)) {
            return PropertyDetailViewModel(propertyDataSource, propertyAndPropertyPhotoDataSource, propertyAndLocationOfInterestDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}
