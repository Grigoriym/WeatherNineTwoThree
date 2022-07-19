package com.grappim.weatherninetwothree.domain.dataSource.remote

import com.grappim.weatherninetwothree.data.model.geocoding.CurrentLocationDTO
import com.grappim.weatherninetwothree.data.model.weather.WeatherCurrentDailyDTO
import com.grappim.weatherninetwothree.domain.interactor.getCurrentPlace.GetCurrentPlaceParams
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.interactor.weatherData.WeatherDataParams

interface WeatherRemoteDataSource {
    suspend fun getWeather(
        params: WeatherDataParams
    ): Try<WeatherCurrentDailyDTO, Throwable>

    suspend fun getCurrentLocation(
        params: GetCurrentPlaceParams
    ): Try<List<CurrentLocationDTO>, Throwable>
}