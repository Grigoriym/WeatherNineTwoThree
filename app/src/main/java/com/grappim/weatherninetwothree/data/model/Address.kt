package com.grappim.weatherninetwothree.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val city: String?,
    val countryCode: String,
    val countryName: String,
    val county: String?,
    val label: String,
    val postalCode: String?,
    val state: String?,
    val stateCode: String?
)