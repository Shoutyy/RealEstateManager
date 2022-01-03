package com.example.realestatemanager.ui.map

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.example.realestatemanager.BuildConfig
import com.google.android.gms.maps.model.LatLng
import com.example.realestatemanager.R
import com.example.realestatemanager.network.GoogleStreams
import com.example.realestatemanager.model.geocoding.GeocodingResponse
import com.example.realestatemanager.di.MapInjection
import com.example.realestatemanager.model.MapPropertyModelProcessed
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.toolbar.*

const val PICK_PROPERTY_DATA = "PICK_PROPERTY_DATA"

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private lateinit var mMap: GoogleMap
    private val mapViewModel: MapViewModel by lazy { ViewModelProviders.of(this, MapInjection.provideViewModelFactory(applicationContext)).get(MapViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        configureToolbar()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val newYork = LatLng(40.712775, -74.005973)
        val zoom = 10.toFloat()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newYork, zoom))
        mMap.setOnMarkerClickListener(this)

        mapViewModel.properties.observe(this, Observer { it.map { mapPropertyModelProcessed ->  setMarkerOnMap(mapPropertyModelProcessed) } })
    }

    private fun setMarkerOnMap(mapPropertyModelProcessed: MapPropertyModelProcessed) {
        val disposable: Disposable =
            GoogleStreams.streamFetchGeocoding(mapPropertyModelProcessed.addressGeocoding, BuildConfig.GEOCODING_API_KEY)
                .subscribeWith(object : DisposableObserver<GeocodingResponse>() {
                    override fun onNext(geocodingResponse: GeocodingResponse) {
                        Log.e("Geocoding", "On Next")
                        when (geocodingResponse.status) {
                            "OK" -> {
                                val position = LatLng(
                                    geocodingResponse.results[0].geometry.location.lat,
                                    geocodingResponse.results[0].geometry.location.lng
                                )
                                mMap.addMarker(MarkerOptions().position(position))?.tag = mapPropertyModelProcessed.propertyId
                            }
                            "ZERO_RESULTS" -> { }
                            "UNKNOWN_ERROR" -> { }
                        }
                    }
                    override fun onError(e: Throwable) { Log.e("Geocoding", "On Error" + Log.getStackTraceString(e)) }
                    override fun onComplete() { Log.e("Geocoding", "On Complete !!") }
                })
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val propertyId = marker.tag as Int
        val returnIntent = Intent()
        returnIntent.putExtra(PICK_PROPERTY_DATA, propertyId)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
        return false
    }
}