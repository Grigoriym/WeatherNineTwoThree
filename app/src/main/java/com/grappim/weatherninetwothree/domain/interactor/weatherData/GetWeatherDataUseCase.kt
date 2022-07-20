package com.grappim.weatherninetwothree.domain.interactor.weatherData

import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.weather.WeatherDetails
import com.grappim.weatherninetwothree.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend fun invoke(params: WeatherDataParams): Try<WeatherDetails, Throwable> =
        weatherRepository.getWeather(params)

}