package com.grappim.weatherninetwothree.data.model.geocoding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertiesDTO(
    val city: String?,
    val lon: Double,
    val lat: Double,
    @SerialName("country_code")
    val countryCode: String?,
    val country: String?
)