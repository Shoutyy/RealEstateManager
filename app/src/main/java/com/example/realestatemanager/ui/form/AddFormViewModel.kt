package com.example.realestatemanager.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.model.*
import com.example.realestatemanager.util.Utils
import com.example.realestatemanager.repository.*
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
                district = getDistrictForDatabase(district),
                city = getCityForDatabase(city),
                postalCode = postalCode,
                country = getCountryForDatabase(country)
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
                val name = getNamePhoto(index)
                Utils.setInternalBitmap(formPhotoAndWording.photo, path, name, context)
                val propertyPhoto = PropertyPhoto(
                    name = name,
                    wording = getWordingForDatabase(formPhotoAndWording.wording),
                    isThisTheIllustration = index == 0
                )
                insertPropertyPhoto(propertyPhoto, rowIdProperty)
            }
        }
        sendNotification(addFormModelRaw)
    }

    private fun insertPropertyPhoto(propertyPhoto: PropertyPhoto, rowIdProperty: Long) =
        executor.execute {
            val rowIdPropertyPhoto = propertyPhotoDataSource.insertPropertyPhoto(propertyPhoto)
            buildPropertyAndPropertyPhoto(rowIdProperty, rowIdPropertyPhoto)
        }


    private fun buildPropertyAndPropertyPhoto(rowIdProperty: Long, rowIdPropertyPhoto: Long) {
        val propertyAndPropertyPhoto = PropertyAndPropertyPhoto(
            rowIdProperty.toInt(),
            rowIdPropertyPhoto.toInt()
        )
        insertPropertyAndPropertyPhoto(propertyAndPropertyPhoto)
    }

    private fun insertPropertyAndPropertyPhoto(propertyAndPropertyPhoto: PropertyAndPropertyPhoto) =
        executor.execute {
            propertyAndPropertyPhotoDataSource.insertPropertyPhoto(propertyAndPropertyPhoto)
        }

    private fun sendNotification(addFormModelRaw: AddFormModelRaw) {
        /*
        val builder = NotificationCompat.Builder(addFormModelRaw.context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("RealEstateManager")
                .setContentText("Your property as been add.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        NotificationManagerCompat.from(addFormModelRaw.context).notify(0, builder.build())*/
    }

    //---FACTORY---\\
    private fun returnComplementOrNull(complement: String) = if (complement.isNotEmpty()) { complement } else { null }

    private fun getDistrictForDatabase(district: String) =
        when(district) {
            "Bronx" -> District.BRONX
            "Brooklyn" -> District.BROOKLYN
            "Manhattan" -> District.MANHATTAN
            "Queens" -> District.QUEENS
            "Staten Island" -> District.STATEN_ISLAND
            else -> District.BRONX
        }

    private fun getCityForDatabase(city: String) =
        when(city) {
            "New York" -> City.NEW_YORK
            else -> City.NEW_YORK
        }

    private fun getCountryForDatabase(country: String) =
        when(country) {
            "United States" -> Country.UNITED_STATES
            else -> Country.UNITED_STATES
        }

    private fun getTypeForDatabase(type: String) =
        when(type) {
            "Flat" -> Type.FLAT
            "Penthouse" -> Type.PENTHOUSE
            "Mansion" -> Type.MANSION
            "Duplex" -> Type.DUPLEX
            "House" -> Type.HOUSE
            "Loft" -> Type.LOFT
            "Townhouse" -> Type.TOWNHOUSE
            "Condo" -> Type.CONDO
            else -> Type.FLAT
        }

    private fun getAgentIdForDatabase(fullNameAgent: String) =
        when(fullNameAgent) {
            "Harmonie Nee"-> 1
            "Clelie Lafaille" -> 2
            "Elisa Beauvau" -> 3
            "Josette Boutroux" -> 4
            "Albert Lafaille" -> 5
            "Omer Delaplace" -> 6
            "Robert Courtial" -> 7
            "Christopher Gaudreau" -> 8
            else -> 1
        }

    private fun getWordingForDatabase(wording: String?) =
        when(wording) {
            "Street View" -> Wording.STREET_VIEW
            "Living room" -> Wording.LIVING_ROOM
            "Hall" -> Wording.HALL
            "Kitchen" -> Wording.KITCHEN
            "Dining room" -> Wording.DINING_ROOM
            "Bathroom" -> Wording.BATHROOM
            "Balcony" -> Wording.BALCONY
            "Bedroom" -> Wording.BEDROOM
            "Terrace" -> Wording.TERRACE
            "Walk in closet" -> Wording.WALK_IN_CLOSET
            "Office" -> Wording.OFFICE
            "Roof top" -> Wording.ROOF_TOP
            "plan" -> Wording.PLAN
            "Hallway" -> Wording.HALLWAY
            "View" -> Wording.VIEW
            "Garage" -> Wording.GARAGE
            "Swimming pool" -> Wording.SWIMMING_POOL
            "Fitness centre" -> Wording.FITNESS_CENTRE
            "Spa" -> Wording.SPA
            "Cinema" -> Wording.CINEMA
            "Conference" -> Wording.CONFERENCE
            "Stairs" -> Wording.STAIRS
            "Garden" -> Wording.GARDEN
            "Floor" -> Wording.FLOOR
            else -> Wording.STREET_VIEW
        }

    private fun getNamePhoto(index: Int) = "$index.png"
}



