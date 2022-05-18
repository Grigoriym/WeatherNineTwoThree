package com.grappim.weatherninetwothree.data.model.geocoding

import kotlinx.serialization.Serializable

@Serializable
data class PropertiesDTO(
    val city: String?,
    val lon: Double,
    val lat: Double
)