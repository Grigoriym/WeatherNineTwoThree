package com.grappim.weatherninetwothree.data.model.geocoding

import kotlinx.serialization.Serializable

@Serializable
data class AutocompleteResponseDTO(
    val features: List<FeatureDTO>
)