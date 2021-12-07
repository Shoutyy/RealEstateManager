package com.example.realestatemanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.realestatemanager.util.Utils.isInternetAvailable
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowNetworkInfo
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class UtilsBisInstrumented {
    private var connectivityManager: ConnectivityManager? = null
    private var shadowOfActiveNetworkInfo: ShadowNetworkInfo? = null

    @Before
    fun setUp() {
        connectivityManager = ApplicationProvider.getApplicationContext<Context>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        shadowOfActiveNetworkInfo =
            Shadows.shadowOf(Objects.requireNonNull(connectivityManager!!).activeNetworkInfo)
    }

    @Test
    fun getActiveNetworkInfo_shouldInitializeItself() {
        assertThat(connectivityManager!!.activeNetworkInfo).isNotNull()
    }


    @Test
    fun getActiveNetworkInfo_shouldReturnTrueCorrectly() {
        shadowOfActiveNetworkInfo!!.setConnectionStatus(NetworkInfo.State.CONNECTED)
        assertThat(isInternetAvailable(ApplicationProvider.getApplicationContext())).isTrue()

        shadowOfActiveNetworkInfo!!.setConnectionStatus(NetworkInfo.State.CONNECTING)
        assertThat(isInternetAvailable(ApplicationProvider.getApplicationContext())).isFalse()

        shadowOfActiveNetworkInfo!!.setConnectionStatus(NetworkInfo.State.DISCONNECTED)
        assertThat(isInternetAvailable(ApplicationProvider.getApplicationContext())).isFalse()
    }

}