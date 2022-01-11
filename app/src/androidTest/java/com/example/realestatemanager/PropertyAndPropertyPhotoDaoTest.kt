package com.example.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.example.realestatemanager.database.AppDatabase
import com.example.realestatemanager.model.PropertyAndPropertyPhoto
import com.example.realestatemanager.model.PropertyPhoto
import com.example.realestatemanager.model.Wording
import com.example.realestatemanager.util.LiveDataTestUtil
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4ClassRunner::class)
class PropertyAndPropertyPhotoDaoTest {

    private lateinit var db: AppDatabase
    private val propertyPhotoId = 89
    private val propertyId = 65

    private val composition1 = PropertyAndPropertyPhoto(propertyId, propertyPhotoId)
    private val composition2 = PropertyAndPropertyPhoto(propertyId, 90)
    private val composition3 = PropertyAndPropertyPhoto(propertyId, 91)

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        insertManyPropertyPhotoAndComposition()
    }

    private fun insertManyPropertyPhotoAndComposition() {
        val propertyPhoto1 = PropertyPhoto(
            id = propertyPhotoId,
            name = "0.jpg",
            wording = Wording.BALCONY,
            isThisTheIllustration = true
        )
        db.propertyPhotoDao().insertPropertyPhoto(propertyPhoto1)

        val propertyPhoto2 = PropertyPhoto(
            id = 90,
            name = "1.jpg",
            wording = Wording.KITCHEN,
            isThisTheIllustration = false
        )
        db.propertyPhotoDao().insertPropertyPhoto(propertyPhoto2)

        val propertyPhoto3 = PropertyPhoto(
            id = 91,
            name = "2.jpg",
            wording = Wording.BEDROOM,
            isThisTheIllustration = false
        )
        db.propertyPhotoDao().insertPropertyPhoto(propertyPhoto3)
        db.propertyAndPropertyPhotoDao().insertPropertyPhoto(composition1)
        db.propertyAndPropertyPhotoDao().insertPropertyPhoto(composition2)
        db.propertyAndPropertyPhotoDao().insertPropertyPhoto(composition3)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCompositionFromDatabase() {
        val compositions = LiveDataTestUtil.getValue(db.propertyAndPropertyPhotoDao().getPropertyPhotos(propertyId))
        assertTrue(compositions?.size == 3
                && compositions[0].propertyId == composition1.propertyId
                && compositions[0].propertyPhotoId == composition1.propertyPhotoId
                && compositions[0].propertyPhoto?.wording == Wording.BALCONY)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetIllustrationFromDatabase() {
        val composition = LiveDataTestUtil.getValue(db.propertyAndPropertyPhotoDao().getPropertyIllustration(propertyId, true))

        assertTrue(composition!!.propertyPhoto!!.isThisTheIllustration)
    }

    @Test
    @Throws(Exception::class)
    fun deleteCompositionFromDatabase() {
        db.propertyAndPropertyPhotoDao().deletePropertyPhoto(propertyId, propertyPhotoId)
        val compositions = LiveDataTestUtil.getValue(db.propertyAndPropertyPhotoDao().getPropertyPhotos(propertyId))

        assertTrue(compositions?.size == 2)
    }

}

