package com.grappim.weatherninetwothree.ui.weatherDetails.viewModel

import com.grappim.weatherninetwothree.domain.model.weather.WeatherDetails

sealed class WeatherDataResult {

    object Loading : WeatherDataResult()
    data class SuccessResult(
        val result: WeatherDetails
    ) : WeatherDataResult()

    data class ErrorResult(val e: Throwable) : WeatherDataResult()
}