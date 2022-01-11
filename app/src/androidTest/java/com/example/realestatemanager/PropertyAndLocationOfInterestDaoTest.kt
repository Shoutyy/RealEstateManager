package com.example.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.example.realestatemanager.database.AppDatabase
import com.example.realestatemanager.model.PropertyAndLocationOfInterest
import com.example.realestatemanager.model.LocationOfInterest
import com.example.realestatemanager.util.LiveDataTestUtil
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4ClassRunner::class)
class PropertyAndLocationOfInterestDaoTest {

    private lateinit var db: AppDatabase
    private val propertyId = 65
    private val locationOfInterestId = LocationOfInterest.PARK.ordinal
    private val composition = PropertyAndLocationOfInterest(propertyId, locationOfInterestId)

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        db.propertyAndLocationOfInterestDao().insertLocationOfInterest(composition)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCompositionFromDatabase() {
        val compositions = LiveDataTestUtil.getValue(db.propertyAndLocationOfInterestDao().getLocationsOfInterest(propertyId))

        assertTrue(compositions?.get(0)?.propertyId == composition.propertyId && compositions[0].locationOfInterestId == composition.locationOfInterestId )
    }

    @Test
    @Throws(Exception::class)
    fun insertAndDeleteCompositionFromDatabase() {
        db.propertyAndLocationOfInterestDao().deleteLocationOfInterest(propertyId, locationOfInterestId)
        val compositions = LiveDataTestUtil.getValue(db.propertyAndLocationOfInterestDao().getLocationsOfInterest(propertyId))

        assertTrue(compositions.isNullOrEmpty())
    }

}