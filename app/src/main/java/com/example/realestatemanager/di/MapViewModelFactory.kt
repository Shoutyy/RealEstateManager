package com.example.realestatemanager.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.realestatemanager.ui.map.MapViewModel
import com.example.realestatemanager.repository.PropertyDataRepository

class MapViewModelFactory (
    private val propertyDataSource: PropertyDataRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(propertyDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}