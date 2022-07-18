package com.grappim.weatherninetwothree.domain.repository

import com.grappim.weatherninetwothree.domain.interactor.get_current_place.GetCurrentPlaceParams
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.interactor.weather_data.WeatherDataParams
import com.grappim.weatherninetwothree.domain.model.location.CurrentLocation
import com.grappim.weatherninetwothree.domain.model.weather.WeatherDetails

interface WeatherRepository {

    suspend fun getWeather(
        params: WeatherDataParams
    ): Try<WeatherDetails, Throwable>

    suspend fun getCurrentLocation(
        params: GetCurrentPlaceParams
    ): Try<CurrentLocation, Throwable>

}