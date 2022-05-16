package com.grappim.weatherninetwothree.domain.model.weather

data class WeatherDetails(
    val timezone: String,
    val currentWeather: CurrentWeather,
    val daily: List<DailyWeather>
)

data class CurrentWeather(
    val icon: String,
    val description: String,
    val main: String,
    val temp: String,
    val feelsLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val weatherCondition: String,
    val currentTime: String
)

data class DailyWeather(
    val morningTemp: Double,
    val dayTemperature: String,
    val eveningTemperature: Double,
    val nightTemperature: String,
    val humidity: Int,
    val windSpeed: Double,
    val weatherCondition: String,
    val icon: String,
    val description: String,
    val main: String,
    val time: String
)