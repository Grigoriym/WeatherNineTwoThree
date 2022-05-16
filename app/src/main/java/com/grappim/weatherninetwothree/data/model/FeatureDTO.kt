package com.grappim.weatherninetwothree.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FeatureDTO(
    val properties: PropertiesDTO
)

@Serializable
data class PropertiesDTO(
    val city: String?,
    val lon: Double,
    val lat: Double
)