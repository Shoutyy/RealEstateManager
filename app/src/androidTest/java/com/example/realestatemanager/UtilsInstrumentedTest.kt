package com.example.realestatemanager

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.realestatemanager.ui.MainActivity
import com.example.realestatemanager.util.Utils
import android.content.Context
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import androidx.test.core.app.ApplicationProvider.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.lang.Exception

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UtilsInstrumentedTest {

    private var wifiManager: WifiManager? = null
    private var telephonyManager: TelephonyManager? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        wifiManager = getApplicationContext<Context>().getSystemService(Context.WIFI_SERVICE) as WifiManager
        telephonyManager = getApplicationContext<Context>().getSystemService(Context.TELECOM_SERVICE) as TelephonyManager
    }

    @Test
    fun checkIf_InternetIsAvailable() {
        assertEquals(true, Utils.isInternetAvailable(getApplicationContext<MainActivity>()))
    }

    @Test
    fun checkIf_InternetIsAvailable_WifiDisableDataEnable() {
        if (wifiManager!!.isWifiEnabled) {
            wifiManager?.isWifiEnabled = false
        }
        if (!telephonyManager!!.isDataEnabled) {
            telephonyManager?.isDataEnabled = true
        }
        assertEquals(true, Utils.isInternetAvailable(getApplicationContext<MainActivity>()))
    }

    @Test
    fun checkIf_InternetIsAvailable_WifiEnableDataEnable() {
        if (wifiManager!!.isWifiEnabled) {
            wifiManager?.isWifiEnabled = false
        }
        if (!telephonyManager!!.isDataEnabled) {
            telephonyManager?.isDataEnabled = true
        }
        assertEquals(true, Utils.isInternetAvailable(getApplicationContext<MainActivity>()))
    }

    @Test
    fun checkIf_InternetIsAvailable_WifiDisableDataDisable() {
        if (wifiManager!!.isWifiEnabled) {
            wifiManager?.isWifiEnabled = false
        }
        if (telephonyManager!!.isDataEnabled) {
            telephonyManager?.isDataEnabled = false
        }
        assertEquals(false, Utils.isInternetAvailable(getApplicationContext<MainActivity>()))
    }

}