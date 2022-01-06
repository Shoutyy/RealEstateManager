package com.example.realestatemanager.ui.property

import androidx.lifecycle.*
import android.content.Context
import android.text.TextUtils
import com.example.realestatemanager.util.Utils
import com.example.realestatemanager.model.*
import com.example.realestatemanager.repository.PropertyDataRepository
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.realestatemanager.repository.PropertyPhotoDataRepository
import com.example.realestatemanager.repository.PropertyAndPropertyPhotoDataRepository

class PropertyListViewModel (
    private val propertyDataSource: PropertyDataRepository,
        private val propertyAndPropertyPhotoDataRepository: PropertyAndPropertyPhotoDataRepository) : ViewModel() {

    private var _allProperties: LiveData<List<PropertyModelProcessed>> = Transformations.map(propertyDataSource.getAllProperties()) { it.map { property -> buildPropertyModelProcessed(property) } }
    val allProperties: LiveData<List<PropertyModelProcessed>> = _allProperties

    fun getProperties(propertiesId: List<Int>): LiveData<List<PropertyModelProcessed>> =
        Transformations.map(propertyDataSource.getProperties(buildRequest(propertiesId))) { it.map { property -> buildPropertyModelProcessed(property) } }

    fun getPropertyIllustration(propertyId: Int, context: Context): LiveData<IllustrationModelProcessed> =
        Transformations.map(propertyAndPropertyPhotoDataRepository.getPropertyIllustration(propertyId, true)) { getIllustrationModelProcessed(it, context) }

    //factory
    private fun buildPropertyModelProcessed(property: Property) =
        PropertyModelProcessed(
            propertyId = property.id,
            path = property.address?.path,
            type = Utils.fromTypeToString(property.type),
            district = Utils.fromDistrictToString(property.address?.district),
            price = Utils.fromPriceToString(property.price)
        )

    private fun buildRequest(propertiesId: List<Int>): SimpleSQLiteQuery {
        val valueList = ArrayList<Any>()
        val questionMarkList = ArrayList<String>()
        for (propertyId in propertiesId) {
            valueList.add(propertyId)
            questionMarkList.add("?")
        }
        val questionMarkJoin = TextUtils.join(", ", questionMarkList)
        return SimpleSQLiteQuery("SELECT * FROM Property INNER JOIN Address ON Property.addressId = Address.address_id INNER JOIN Agent ON Property.agentId = Agent.agent_id WHERE Property.id IN ($questionMarkJoin)", valueList.toArray())
    }

    private fun getIllustrationModelProcessed(composition: PropertyAndPropertyPhoto, context: Context) =
        IllustrationModelProcessed(
            propertyId = composition.propertyId,
            illustration = Utils.getInternalBitmap(composition.propertyId.toString(), composition.propertyPhoto?.name, context)
        )
}
