package com.grappim.weatherninetwothree.data.model.weather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeelsLike(
    @SerialName("day")
    val day: Double,
    @SerialName("eve")
    val eve: Double,
    @SerialName("morn")
    val morn: Double,
    @SerialName("night")
    val night: Double
)