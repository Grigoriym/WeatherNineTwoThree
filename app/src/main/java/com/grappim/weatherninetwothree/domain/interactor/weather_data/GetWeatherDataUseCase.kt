package com.grappim.weatherninetwothree.domain.interactor.weather_data

import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.weather.WeatherDetails

interface GetWeatherDataUseCase {

    suspend operator fun invoke(
        params: WeatherDataParams
    ): Try<WeatherDetails, Throwable>
}