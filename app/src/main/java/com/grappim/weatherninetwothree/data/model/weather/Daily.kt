package com.grappim.weatherninetwothree.data.model.weather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Daily(
    @SerialName("clouds")
    val clouds: Int,
    @SerialName("dew_point")
    val dewPoint: Double,
    @SerialName("dt")
    val dt: Long,
    @SerialName("feels_like")
    val feelsLike: FeelsLike,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("moon_phase")
    val moonPhase: Double,
    @SerialName("moonrise")
    val moonrise: Int,
    @SerialName("moonset")
    val moonset: Int,
    @SerialName("pop")
    val pop: Double,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("rain")
    val rain: Double?,
    @SerialName("sunrise")
    val sunrise: Int,
    @SerialName("sunset")
    val sunset: Int,
    @SerialName("temp")
    val temp: Temp,
    @SerialName("uvi")
    val uvi: Double,
    @SerialName("weather")
    val weather: List<WeatherX>,
    @SerialName("wind_deg")
    val windDeg: Int,
    @SerialName("wind_gust")
    val windGust: Double,
    @SerialName("wind_speed")
    val windSpeed: Double
)