package com.grappim.weatherninetwothree.core.location

sealed class GetLocationResult {
    data class Success(
        val longitude: Double,
        val latitude: Double
    ) : GetLocationResult()

    data class Error(
        val errorMsg: String
    ) : GetLocationResult()
}
