package com.example.realestatemanager.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.model.*
import com.example.realestatemanager.repository.AddressDataRepository
import com.example.realestatemanager.repository.AgentDataRepository
import com.example.realestatemanager.repository.PropertyAndLocationOfInterestDataRepository
import com.example.realestatemanager.repository.PropertyDataRepository
import java.util.concurrent.Executor

class FormViewModel (
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val propertyAndLocationOfInterestDataSource: PropertyAndLocationOfInterestDataRepository,
        private val executor: Executor) : ViewModel() {

    private var _fullNameAgents: LiveData<List<String>> = Transformations.map(agentDataSource.getAgents()) { list -> list.map { agent -> agent.firstName + " " + agent.name } }
    val fullNameAgents: LiveData<List<String>> = _fullNameAgents

    fun startBuildingModelsForDatabase(formModelRaw: FormModelRaw) {
        buildAddressModel(formModelRaw)
    }

    private fun buildAddressModel(formModelRaw: FormModelRaw) {
        val address: Address
        with(formModelRaw) {
            address = Address(
                path = path,
                complement = returnComplementOrNull(complement),
                district = getDistrictForDatabase(district),
                city = getCityForDatabase(city),
                postalCode = postalCode,
                country = getCountryForDatabase(country)
            )
        }
        insertAddress(formModelRaw, address)
    }

    private fun insertAddress(formModelRaw: FormModelRaw, address: Address) =
        executor.execute {
            val rowIdAddress = addressDataSource.insertAddress(address)
            buildPropertyModel(formModelRaw, rowIdAddress)
    }

    private fun buildPropertyModel(formModelRaw: FormModelRaw, rowIdAddress: Long) {
        val property: Property
        with(formModelRaw) {
            property = Property(
                type = getTypeForDatabase(type),
                price = price.toLong(),
                surface = surface.toInt(),
                rooms = rooms.toInt(),
                bedrooms = bedrooms.toInt(),
                bathrooms = bathrooms.toInt(),
                description = description,
                addressId = rowIdAddress.toInt(),
                available = available,
                entryDate = entryDate,
                saleDate = null,
                agentId = getAgentIdForDatabase(fullNameAgent)
            )
        }
        insertProperty(formModelRaw, property)
    }

    private fun insertProperty(formModelRaw: FormModelRaw, property: Property) =
        executor.execute {
            val rowIdProperty = propertyDataSource.insertProperty(property)
            buildPropertyAndLocationOfInterestDataSource(formModelRaw, rowIdProperty)
    }

    private fun buildPropertyAndLocationOfInterestDataSource(formModelRaw: FormModelRaw, rowIdProperty: Long) {
        with(formModelRaw) {
            if (school){
                val propertyAndLocationOfInterest = PropertyAndLocationOfInterest(
                    propertyId = rowIdProperty.toInt(),
                    locationOfInterestId = LocationOfInterest.SCHOOL.ordinal
                )
                insertPropertyAndLocationOfInterest(propertyAndLocationOfInterest)
            }
            if (commerces) {
                val propertyAndLocationOfInterest = PropertyAndLocationOfInterest(
                    propertyId = rowIdProperty.toInt(),
                    locationOfInterestId = LocationOfInterest.COMMERCES.ordinal
                )
                insertPropertyAndLocationOfInterest(propertyAndLocationOfInterest)
            }
            if (park) {
                val propertyAndLocationOfInterest = PropertyAndLocationOfInterest(
                    propertyId = rowIdProperty.toInt(),
                    locationOfInterestId = LocationOfInterest.PARK.ordinal
                )
                insertPropertyAndLocationOfInterest(propertyAndLocationOfInterest)
            }
            if (subways) {
                val propertyAndLocationOfInterest = PropertyAndLocationOfInterest(
                    propertyId = rowIdProperty.toInt(),
                    locationOfInterestId = LocationOfInterest.SUBWAYS.ordinal
                )
                insertPropertyAndLocationOfInterest(propertyAndLocationOfInterest)
            }
            if (train) {
                val propertyAndLocationOfInterest = PropertyAndLocationOfInterest(
                    propertyId = rowIdProperty.toInt(),
                    locationOfInterestId = LocationOfInterest.TRAIN.ordinal
                )
                insertPropertyAndLocationOfInterest(propertyAndLocationOfInterest)
            }
        }
    }

    private fun insertPropertyAndLocationOfInterest(propertyAndLocationOfInterest: PropertyAndLocationOfInterest) =
        executor.execute {
            propertyAndLocationOfInterestDataSource.insertLocationOfInterest(propertyAndLocationOfInterest)
        }

    //---FACTORY---\\
    private fun returnComplementOrNull(complement: String): String? {
        return if (complement.isNotEmpty()) {
            complement
        } else {
            null
        }
    }

    private fun getDistrictForDatabase(district: String): District {
        return when(district) {
            "Bronx" -> District.BRONX
            "Brooklyn" -> District.BROOKLYN
            "Manhattan" -> District.MANHATTAN
            "Queens" -> District.QUEENS
            "Staten Island" -> District.STATEN_ISLAND
            else -> return District.BRONX //TODO: Why ?
        }
    }

    private fun getCityForDatabase(city: String): City {
        return when(city) {
            "New York" -> City.NEW_YORK
            else -> City.NEW_YORK //TODO: Why ?
        }
    }

    private fun getCountryForDatabase(country: String): Country {
        return when(country) {
            "United States" -> Country.UNITED_STATES
            else -> Country.UNITED_STATES //TODO: Why ?
        }
    }
    private fun getTypeForDatabase(type: String): Type {
        return when(type) {
            "Flat" -> Type.FLAT
            "Penthouse" -> Type.PENTHOUSE
            "Mansion" -> Type.MANSION
            "Duplex" -> Type.DUPLEX
            "House" -> Type.HOUSE
            "Loft" -> Type.LOFT
            "Townhouse" -> Type.TOWNHOUSE
            "Condo" -> Type.CONDO
            else -> Type.FLAT //TODO: Why ?
        }
    }

    private fun getAgentIdForDatabase(fullNameAgent: String): Int {
        return when(fullNameAgent) {
            "Tony Stark" -> 1
            "Peter Parker" -> 2
            "Steve Rogers" -> 3
            "Natasha Romanoff" -> 4
            "Bruce Banner" -> 5
            "Clinton Barton" -> 6
            "Carol Denvers" -> 7
            "Wanda Maximoff" -> 8
            else -> 1 //TODO: Why ?
        }
    }

}



