package com.example.realestatemanager.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.realestatemanager.ui.searchForm.SearchFormViewModel
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.PropertyAndLocationOfInterestDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import java.lang.IllegalArgumentException

class SearchFormViewModelFactory(private val propertyDataSource: PropertyDataRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchFormViewModel::class.java)) {
            return SearchFormViewModel(propertyDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}