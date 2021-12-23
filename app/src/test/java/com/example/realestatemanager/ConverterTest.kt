package com.example.realestatemanager

import com.example.realestatemanager.database.converter.*
import com.example.realestatemanager.model.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.sql.Date

class DistrictConverterTest {

    @Test
    fun fromDistrict_DistrictToInt() {
        assertEquals(2 , DistrictConverter.fromDistrict(District.MANHATTAN))
    }

    @Test
    fun toDistrict_IntToDistrict() {
        assertEquals(District.QUEENS, DistrictConverter.toDistrict(3))
    }
}

class CityConverterTest {

    @Test
    fun fromCity_CityToInt() {
        assertEquals(0 , CityConverter.fromCity(City.NEW_YORK))
    }

    @Test
    fun toCity_IntToCity() {
        assertEquals(City.NEW_YORK, CityConverter.toCity(0))
    }
}

class CountryConverterTest {

    @Test
    fun fromCountry_CountryToInt() {
        assertEquals(0 , CountryConverter.fromCountry(Country.UNITED_STATES))
    }

    @Test
    fun toCountry_IntToCountry() {
        assertEquals(Country.UNITED_STATES, CountryConverter.toCountry(0))
    }
}

class TypeConverterTest {

    @Test
    fun fromType_TypeToInt() {
        assertEquals(4 , TypeConverter.fromType(Type.HOUSE))
    }

    @Test
    fun toType_IntToType() {
        assertEquals(Type.DUPLEX, TypeConverter.toType(3))
    }
}


class LocationsOfInterestConverterTest  {

    @Test
    fun fromLocationsOfInterest_LocationsOfInterestToString() {
        assertEquals(2,
            LocationOfInterestConverter
                .fromLocationOfInterest(LocationOfInterest.PARK))
    }

    @Test
    fun toLocationsOfInterest_StringToLocationsOfInterest() {
        assertEquals(LocationOfInterest.COMMERCES,
            LocationOfInterestConverter.toLocationOfInterest(1))
    }

}

class DateConverterTest {

    @Test
    fun fromDate_DistrictToInt() {
        assertEquals(156748945612 , DateConverter.fromDate(Date(156748945612)))
    }

    @Test
    fun toDate_IntToDistrict() {
        assertEquals(Date(1156489789561), DateConverter.toDate(1156489789561))
    }

}

class WordingConverterTest {

    @Test
    fun fromWording_toInt() {
        assertEquals(7, WordingConverter.fromWording(Wording.BEDROOM))
    }

    @Test
    fun toWording_IntToWording() {
        assertEquals(Wording.ROOF_TOP, WordingConverter.toWording(11))
    }

}
