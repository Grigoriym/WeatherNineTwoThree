package com.grappim.weatherninetwothree.domain.repository

import com.grappim.weatherninetwothree.domain.interactor.GetCurrentPlaceUseCase
import com.grappim.weatherninetwothree.domain.interactor.GetWeatherDataUseCase
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.location.CurrentLocation
import com.grappim.weatherninetwothree.domain.model.weather.WeatherDetails
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeather(
        params: GetWeatherDataUseCase.Params
    ): Flow<Try<WeatherDetails>>

    fun getCurrentLocation(
        params: GetCurrentPlaceUseCase.Params
    ): Flow<Try<CurrentLocation>>

}