package com.grappim.weatherninetwothree.data.model.geocoding

import kotlinx.serialization.Serializable

@Serializable
data class FeatureDTO(
    val properties: PropertiesDTO
)