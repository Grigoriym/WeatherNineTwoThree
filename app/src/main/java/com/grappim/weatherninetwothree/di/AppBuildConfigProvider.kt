package com.grappim.weatherninetwothree.di

data class AppBuildConfigProvider(
    val apiOpenWeatherHost: String,
    val openWeatherIconHost: String,
    val openWeatherApiKey: String,
    val geoApifyHost: String,
    val geoApifyKey: String
)
