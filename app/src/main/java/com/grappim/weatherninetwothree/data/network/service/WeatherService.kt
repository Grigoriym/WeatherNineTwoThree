package com.grappim.weatherninetwothree.data.network.service

import com.grappim.weatherninetwothree.data.model.geocoding.CurrentLocationDTO
import com.grappim.weatherninetwothree.data.model.weather.WeatherCurrentDailyDTO
import com.grappim.weatherninetwothree.data.network.interceptors.RequestWithOneWeatherApi
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/onecall")
    @RequestWithOneWeatherApi
    suspend fun getCurrentAndDailyWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String = "minutely,alerts,hourly",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "en"
    ): WeatherCurrentDailyDTO

    @GET("geo/1.0/reverse")
    @RequestWithOneWeatherApi
    suspend fun getReverseGeocoding(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("limit") limit: Int = 1
    ): List<CurrentLocationDTO>
}