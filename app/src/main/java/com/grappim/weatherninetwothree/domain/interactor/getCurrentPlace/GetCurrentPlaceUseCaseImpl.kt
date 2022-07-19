package com.grappim.weatherninetwothree.domain.interactor.getCurrentPlace

import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.location.CurrentLocation
import com.grappim.weatherninetwothree.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentPlaceUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : GetCurrentPlaceUseCase {
    override suspend fun invoke(params: GetCurrentPlaceParams): Try<CurrentLocation, Throwable> =
        weatherRepository.getCurrentLocation(params)

}