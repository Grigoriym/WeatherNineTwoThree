package com.grappim.weatherninetwothree.data.model.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherCurrentDailyDTO(
    @SerialName("current")
    val current: Current,
    @SerialName("daily")
    val daily: List<Daily>,
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("timezone_offset")
    val timezoneOffset: Int
)