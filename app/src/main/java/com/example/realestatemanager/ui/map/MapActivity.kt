package com.example.realestatemanager.ui.map

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import android.os.Bundle
import android.Manifest
import androidx.core.content.ContextCompat
import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.example.realestatemanager.BuildConfig
import com.google.android.gms.maps.model.LatLng
import com.example.realestatemanager.R
import com.sembozdemir.permissionskt.askPermissions
import com.sembozdemir.permissionskt.handlePermissionsResult
import com.example.realestatemanager.network.GoogleStreams
import com.example.realestatemanager.model.geocoding.GeocodingResponse
import com.example.realestatemanager.di.MapInjection
import com.example.realestatemanager.model.MapPropertyModelProcessed
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.activity_map.*

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        handlePermissionsResult(requestCode, permissions, grantResults)
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        askPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION) {
            onGranted {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MapActivity)
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            val latitude =  location.latitude
                            val longitude = location.longitude
                            mMap.isMyLocationEnabled = true
                            mMap.uiSettings.isMyLocationButtonEnabled = true
                            val myLocation = LatLng(latitude, longitude)
                            val zoom = 11.toFloat()
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, zoom))
                        } else {
                            Snackbar.make(coordinatorLayout_map_activity, getString(R.string.map_need_location), Snackbar.LENGTH_LONG).show()
                        }
                        mMap.setOnMarkerClickListener(this@MapActivity)
                    }

                mapViewModel.allProperties.observe(this@MapActivity, Observer { it.map { propertyModelProcessed ->  setMarkerOnMap(propertyModelProcessed) } })
            }
            onShowRationale { request ->
                Snackbar.make(coordinatorLayout_map_activity, getString(R.string.map_need_location_permission), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.map_location_permission_retry)) { request.retry() }
                    .setActionTextColor(ContextCompat.getColor(applicationContext, R.color.redAccent))
                    .show()
            }
        }
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