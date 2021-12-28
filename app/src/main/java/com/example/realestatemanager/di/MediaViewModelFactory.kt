package com.example.realestatemanager.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.realestatemanager.ui.media.MediaViewModel
import com.example.realestatemanager.repository.PropertyAndPropertyPhotoDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository

class MediaViewModelFactory (
    private val propertyDataSource: PropertyDataRepository,
    private val propertyAndPropertyPhotoDataSource: PropertyAndPropertyPhotoDataRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MediaViewModel::class.java)) {
            return MediaViewModel(propertyDataSource, propertyAndPropertyPhotoDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}