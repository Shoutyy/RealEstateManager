package com.example.realestatemanager.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.realestatemanager.ui.form.FormViewModel
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.AgentDataRepository
import com.example.realestatemanager.repository.PropertyAndLocationOfInterestDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import java.lang.IllegalArgumentException
import java.util.concurrent.Executor

class FormViewModelFactory(
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val propertyAndLocationOfInterestDataSource: PropertyAndLocationOfInterestDataRepository,
        private val executor: Executor): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormViewModel::class.java)) {
            return FormViewModel(propertyDataSource, addressDataSource, agentDataSource, propertyAndLocationOfInterestDataSource, executor) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel Class")
    }

}