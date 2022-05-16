package com.grappim.weatherninetwothree.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AutocompleteResponseDTO(
    val features: List<FeatureDTO>
)