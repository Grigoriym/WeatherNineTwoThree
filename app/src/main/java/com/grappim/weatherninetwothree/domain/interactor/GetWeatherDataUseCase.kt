package com.grappim.weatherninetwothree.domain.interactor

import com.grappim.weatherninetwothree.di.IoDispatcher
import com.grappim.weatherninetwothree.domain.FlowUseCase
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.weather.WeatherDetails
import com.grappim.weatherninetwothree.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val weatherRepository: WeatherRepository
) : FlowUseCase<GetWeatherDataUseCase.Params, WeatherDetails>(ioDispatcher) {

    data class Params(
        val latitude: Double,
        val longitude: Double
    )

    override fun execute(params: Params): Flow<Try<WeatherDetails>> =
        weatherRepository.getWeather(params)
}