package com.grappim.weatherninetwothree.data.model.geocoding

import kotlinx.serialization.Serializable

@Serializable
data class CurrentLocationDTO(
    val name: String,
    val country: String
)
