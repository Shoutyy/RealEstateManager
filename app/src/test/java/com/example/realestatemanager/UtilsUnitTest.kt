package com.example.realestatemanager

import com.example.realestatemanager.util.Utils
import org.junit.Test

import org.junit.Assert.assertEquals
import java.text.SimpleDateFormat
import java.util.*
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UtilsUnitTest {

    @Test
    fun todayDate_isInCorrectFormat() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val todayDate = dateFormat.format(Date())
        assertEquals(todayDate, Utils.todayDate)
    }

    @Test
    fun convert_euroToDollar_isCorrect() {
        assertEquals(100, Utils.convertEuroToDollar(81))
    }
}