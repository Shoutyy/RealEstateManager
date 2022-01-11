package com.example.realestatemanager.ui.form

import androidx.lifecycle.ViewModel
import android.content.Context
import com.example.realestatemanager.model.*
import com.example.realestatemanager.util.Utils
import com.example.realestatemanager.repository.*
import java.util.concurrent.Executor

class GetUpdateFormViewModel(
    private val propertyDataSource: PropertyDataRepository,
    private val addressDataSource: AddressDataRepository,
    private val propertyAndLocationOfInterestDataSource: PropertyAndLocationOfInterestDataRepository,
    private val propertyPhotoDataSource: PropertyPhotoDataRepository,
    private val propertyAndPropertyPhotoDataSource: PropertyAndPropertyPhotoDataRepository,
    private val executor: Executor) : ViewModel() {

    fun deletePropertyAndPropertyPhoto(propertyPhotos: List<FormPhotoAndWording>, propertyId: Int, context: Context) =
        executor.execute {
            propertyPhotos.forEach { formPhotoAndWording ->
                if (formPhotoAndWording.id != null) {
                    propertyAndPropertyPhotoDataSource.deletePropertyPhoto(propertyId, formPhotoAndWording.id)
                }
                deletePropertyPhotoOnInternalStorage(propertyId, formPhotoAndWording.name, context)
                deletePropertyPhoto(formPhotoAndWording.id)
            }
        }

    private fun deletePropertyPhotoOnInternalStorage(propertyId: Int, name: String?, context: Context) {
        Utils.deleteInternalBitmap(propertyId.toString(), name, context)
    }

    private fun deletePropertyPhoto(propertyPhotoId: Int?) =
        executor.execute {
            if (propertyPhotoId != null) {
                propertyPhotoDataSource.deletePropertyPhoto(propertyPhotoId)
            }
        }

    fun insertPropertyPhotos(propertyPhotos: List<FormPhotoAndWording>, propertyId: Int, lastName: String?,  context: Context) =
        executor.execute {
            propertyPhotos.forEachIndexed { index, formPhotoAndWording ->
                val lastInt = lastName?.removeSuffix(".jpg")?.toInt()
                val name = lastInt?.plus(index)?.plus(1).toString()
                Utils.setInternalBitmap(formPhotoAndWording.photo, propertyId.toString(), name, context)
                val propertyPhoto = PropertyPhoto(
                    name = name,
                    wording = Utils.fromStringToWording(formPhotoAndWording.wording),
                    isThisTheIllustration = false
                )
                val rowIdPropertyPhoto = propertyPhotoDataSource.insertPropertyPhoto(propertyPhoto)
                buildPropertyAndPropertyPhoto(propertyId, rowIdPropertyPhoto)
            }
        }


    private fun buildPropertyAndPropertyPhoto(propertyId: Int, rowIdPropertyPhoto: Long) {
        val propertyAndPropertyPhoto = PropertyAndPropertyPhoto(
            propertyId = propertyId,
            propertyPhotoId = rowIdPropertyPhoto.toInt()
        )
        insertPropertyAndPropertyPhoto(propertyAndPropertyPhoto)
    }

    fun updateIllustrationPropertyPhoto(formPhotoAndWording: FormPhotoAndWording) =
        executor.execute {
            if (formPhotoAndWording.id != null && formPhotoAndWording.name != null) {
                val propertyPhoto = PropertyPhoto(
                    id = formPhotoAndWording.id,
                    name = formPhotoAndWording.name,
                    wording = Utils.fromStringToWording(formPhotoAndWording.wording),
                    isThisTheIllustration = true
                )
                propertyPhotoDataSource.updatePropertyPhoto(propertyPhoto)
            }
        }

    private fun insertPropertyAndPropertyPhoto(propertyAndPropertyPhoto: PropertyAndPropertyPhoto) =
        executor.execute {
            propertyAndPropertyPhotoDataSource.insertPropertyPhoto(propertyAndPropertyPhoto)
        }

    fun updateAddress(addressModelRaw: AddressModelRaw) = executor.execute { addressDataSource.updateAddress(buildAddressForDatabase(addressModelRaw)) }

    fun updateProperty(propertyModelRaw: PropertyModelRaw) = executor.execute { propertyDataSource.updateProperty(buildPropertyForDatabase(propertyModelRaw)) }

    fun updateLocationsOfInterest(locationsOfInterestModelRaw: LocationsOfInterestModelRaw) {
        with(locationsOfInterestModelRaw) {
            checkBooleanAndInsertOrDeleteIt(school, propertyId, LocationOfInterest.SCHOOL.ordinal)
            checkBooleanAndInsertOrDeleteIt(commerces, propertyId, LocationOfInterest.COMMERCES.ordinal)
            checkBooleanAndInsertOrDeleteIt(park, propertyId, LocationOfInterest.PARK.ordinal)
            checkBooleanAndInsertOrDeleteIt(subways, propertyId, LocationOfInterest.SUBWAYS.ordinal)
            checkBooleanAndInsertOrDeleteIt(train, propertyId, LocationOfInterest.TRAIN.ordinal)
        }
    }

    private fun checkBooleanAndInsertOrDeleteIt(boolean: Boolean?, propertyId: Int, locationOfInterestId: Int) {
        if (boolean == true) {
            val propertyAndLocationOfInterest = PropertyAndLocationOfInterest(propertyId, locationOfInterestId)
            executor.execute { propertyAndLocationOfInterestDataSource.insertLocationOfInterest(propertyAndLocationOfInterest) }

        } else if (boolean == false) {
            executor.execute { propertyAndLocationOfInterestDataSource.deleteLocationOfInterest(propertyId, locationOfInterestId) }
        }
    }

    //---FACTORY---\\

    private fun buildAddressForDatabase(addressModelRaw: AddressModelRaw) =
        with(addressModelRaw) {
            Address(
                id = id,
                path = path,
                complement = Utils.returnComplementOrNull(complement),
                district = Utils.fromStringToDistrict(district),
                city = Utils.fromStringToCity(city),
                postalCode = postalCode,
                country = Utils.fromStringToCountry(country)
            )
        }

    private fun buildPropertyForDatabase(propertyModelRaw: PropertyModelRaw) =
        with(propertyModelRaw) {
            Property(
                id = id,
                type = Utils.fromStringToType(type),
                price = price.toLong(),
                surface = surface.toInt(),
                rooms = rooms.toInt(),
                bedrooms = bedrooms.toInt(),
                bathrooms = bathrooms.toInt(),
                description = description,
                addressId = addressId,
                available = available,
                entryDate = entryDate,
                saleDate = if (!available) { saleDate } else { null },
                agentId = Utils.fromStringToAgentId(fullNameAgent)
            )
        }

}