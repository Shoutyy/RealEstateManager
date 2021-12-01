package com.example.realestatemanager

import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.realestatemanager.ui.MainActivity
import com.example.realestatemanager.util.Utils

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UtilsInstrumentedTest {

    @Test
    fun checkIf_InternetIsAvailable() {
        assertEquals(true, Utils.isInternetAvailable(ApplicationProvider.getApplicationContext<MainActivity>()))
    }
}