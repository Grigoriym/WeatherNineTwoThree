package com.grappim.weatherninetwothree.core.location

sealed class LocationResult {
    data class Success(
        val longitude: Double,
        val latitude: Double
    ) : LocationResult()

    data class Error(
        val throwable: Throwable?
    ) : LocationResult()
}
