package com.grappim.weatherninetwothree.data.model.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Current(
    @SerialName("dew_point")
    val dewPoint: Double,
    @SerialName("dt")
    val dt: Long,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("sunrise")
    val sunrise: Int,
    @SerialName("sunset")
    val sunset: Int,
    @SerialName("temp")
    val temp: Double,
    @SerialName("weather")
    val weather: List<Weather>,
    @SerialName("wind_speed")
    val windSpeed: Double
)