package com.grappim.weatherninetwothree.core.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import timber.log.Timber

class NineTwoThreeLocationManagerImpl : NineTwoThreeLocationManager {

    private lateinit var fusedLocationProvider: FusedLocationProviderClient

    private lateinit var locationManager: LocationManager
    private var currentLocation: Location? = null
    private var locationByGps: Location? = null
    private var locationByNetwork: Location? = null

    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun getCurrentLocation(
        context: Context,
        onResult: (LocationResult) -> Unit
    ) {
        if (isPlayServicesAvailable(context)) {
            initFusedLocation(context, onResult)
        } else {
            locationManager = context
                .applicationContext
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager
            getLocation(
                onResult = onResult
            )
        }
    }

    private fun isPlayServicesAvailable(context: Context): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
        return resultCode == ConnectionResult.SUCCESS
    }

    private fun initFusedLocation(
        context: Context,
        onResult: (LocationResult) -> Unit
    ) {
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProvider.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    onResult(
                        LocationResult.Success(
                            longitude = location.longitude,
                            latitude = location.latitude
                        )
                    )
                } else {
                    onResult(LocationResult.Error(""))
                }
            }.addOnCanceledListener {
                onResult(LocationResult.Error(""))
            }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(
        onResult: (LocationResult) -> Unit
    ) {
        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        lastKnownLocationByGps?.let {
            locationByGps = lastKnownLocationByGps
        }

        lastKnownLocationByGps?.let {
            locationByGps = lastKnownLocationByGps
        }

        val lastKnownLocationByNetwork =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        lastKnownLocationByNetwork?.let {
            locationByNetwork = lastKnownLocationByNetwork
        }

        if (locationByGps != null &&
            locationByGps?.latitude != null &&
            locationByGps?.longitude != null
        ) {
            currentLocation = locationByGps
            latitude = currentLocation!!.latitude
            longitude = currentLocation!!.longitude
        }
        if (locationByNetwork != null &&
            locationByNetwork?.latitude != null &&
            locationByNetwork?.longitude != null
        ) {
            currentLocation = locationByNetwork
            latitude = currentLocation!!.latitude
            longitude = currentLocation!!.longitude
        }
        Timber.d("locationByNetwork: $locationByNetwork")
        Timber.d("locationByGps: $locationByGps")
        if (longitude != null && latitude != null) {
            onResult(
                LocationResult.Success(
                    longitude = longitude!!,
                    latitude = latitude!!
                )
            )
        } else {
            onResult(
                LocationResult.Error(
                    errorMsg = "Cannot retrieve location data"
                )
            )
        }
    }

}