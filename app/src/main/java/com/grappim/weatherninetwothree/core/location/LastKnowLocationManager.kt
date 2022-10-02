package com.grappim.weatherninetwothree.core.location

interface LastKnowLocationManager {

    fun getCurrentLocation(
        onResult: (GetLocationResult) -> Unit
    )
}