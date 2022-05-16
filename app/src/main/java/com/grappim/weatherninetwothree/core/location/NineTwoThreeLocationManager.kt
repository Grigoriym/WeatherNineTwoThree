package com.grappim.weatherninetwothree.core.location

import android.content.Context

interface NineTwoThreeLocationManager {

    fun getCurrentLocation(
        context: Context,
        onResult: (LocationResult) -> Unit
    )

    fun stopLocationManager()
}