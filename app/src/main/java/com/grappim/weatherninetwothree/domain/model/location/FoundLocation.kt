package com.grappim.weatherninetwothree.domain.model.location

import java.io.Serializable

data class FoundLocation(
    val cityName: String,
    val longitude: Double,
    val latitude: Double
) : Serializable
