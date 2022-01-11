package com.example.realestatemanager.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.model.*
import com.example.realestatemanager.util.Utils
import com.example.realestatemanager.repository.*
import com.example.realestatemanager.R
import java.util.concurrent.Executor

class AddFormViewModel (
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        agentDataSource: AgentDataRepository,
        private val propertyAndLocationOfInterestDataSource: PropertyAndLocationOfInterestDataRepository,
        private val propertyPhotoDataSource: PropertyPhotoDataRepository,
        private val propertyAndPropertyPhotoDataSource: PropertyAndPropertyPhotoDataRepository,
        private val executor: Executor) : ViewModel() {

    private var _fullNameAgents: LiveData<List<String>> = Transformations.map(agentDataSource.getAgents()) { list -> list.map { agent -> agent.firstName + " " + agent.name } }
    val fullNameAgents: LiveData<List<String>> = _fullNameAgents

    fun startBuildingModelsForDatabase(addFormModelRaw: AddFormModelRaw) = buildAddressModel(addFormModelRaw)


    private fun buildAddressModel(addFormModelRaw: AddFormModelRaw) {
        val address: Address
        with(addFormModelRaw) {
            address = Address(
                path = path,
                complement = returnComplementOrNull(complement),
                district = Utils.fromStringToDistrict(district),
                city = Utils.fromStringToCity(city),
                postalCode = postalCode,
                country = Utils.fromStringToCountry(country)
            )
        }
        insertAddress(addFormModelRaw, address)
    }

    private fun insertAddress(addFormModelRaw: AddFormModelRaw, address: Address) =
        executor.execute {
            val rowIdAddress = addressDataSource.insertAddress(address)
            buildPropertyModel(addFormModelRaw, rowIdAddress)
    }

    private fun buildPropertyModel(addFormModelRaw: AddFormModelRaw, rowIdAddress: Long) {
        val property: Property
        with(addFormModelRaw) {
            property = Property(
                type = Utils.fromStringToType(type),
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
                agentId = Utils.fromStringToAgentId(fullNameAgent)
            )
        }
        insertProperty(addFormModelRaw, property)
    }

    private fun insertProperty(addFormModelRaw: AddFormModelRaw, property: Property) =
        executor.execute {
            val rowIdProperty = propertyDataSource.insertProperty(property)
            buildPropertyAndLocationOfInterestDataSource(addFormModelRaw, rowIdProperty)
            buildPropertyPhotoAndSavePhotosOnInternalStorage(addFormModelRaw, rowIdProperty)
    }

    private fun buildPropertyAndLocationOfInterestDataSource(addFormModelRaw: AddFormModelRaw, rowIdProperty: Long) {
        with(addFormModelRaw) {
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

    private fun buildPropertyPhotoAndSavePhotosOnInternalStorage(addFormModelRaw: AddFormModelRaw, rowIdProperty: Long) {
        with(addFormModelRaw) {
            listFormPhotoAndWording.forEachIndexed {index, formPhotoAndWording ->
                val name = Utils.createNamePhoto(index)
                Utils.setInternalBitmap(formPhotoAndWording.photo, rowIdProperty.toString(), name, context)
                val propertyPhoto = PropertyPhoto(
                    name = name,
                    wording = Utils.fromStringToWording(formPhotoAndWording.wording),
                    isThisTheIllustration = index == 0
                )
                insertPropertyPhoto(propertyPhoto, rowIdProperty)
            }
        }
        Utils.sendNotification(addFormModelRaw.context, addFormModelRaw.context.getString(R.string.notification_property_add))
    }

    private fun insertPropertyPhoto(propertyPhoto: PropertyPhoto, rowIdProperty: Long) =
        executor.execute {
            val rowIdPropertyPhoto = propertyPhotoDataSource.insertPropertyPhoto(propertyPhoto)
            buildPropertyAndPropertyPhoto(rowIdProperty, rowIdPropertyPhoto)
        }


    private fun buildPropertyAndPropertyPhoto(rowIdProperty: Long, rowIdPropertyPhoto: Long) {
        val propertyAndPropertyPhoto = PropertyAndPropertyPhoto(
            propertyId = rowIdProperty.toInt(),
            propertyPhotoId = rowIdPropertyPhoto.toInt()
        )
        insertPropertyAndPropertyPhoto(propertyAndPropertyPhoto)
    }

    private fun insertPropertyAndPropertyPhoto(propertyAndPropertyPhoto: PropertyAndPropertyPhoto) =
        executor.execute {
            propertyAndPropertyPhotoDataSource.insertPropertyPhoto(propertyAndPropertyPhoto)
        }

    //---FACTORY---\\
    private fun returnComplementOrNull(complement: String) = if (complement.isNotEmpty()) { complement } else { null }

}



