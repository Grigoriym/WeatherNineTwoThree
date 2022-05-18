package com.grappim.weatherninetwothree.domain.interactor

import com.grappim.weatherninetwothree.di.app.IoDispatcher
import com.grappim.weatherninetwothree.domain.interactor.base.FlowUseCase
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.location.CurrentLocation
import com.grappim.weatherninetwothree.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentPlaceUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val weatherRepository: WeatherRepository
) : FlowUseCase<GetCurrentPlaceUseCase.Params, CurrentLocation>(ioDispatcher) {

    data class Params(
        val longitude: Double,
        val latitude: Double
    )

    override fun execute(params: Params): Flow<Try<CurrentLocation>> =
        weatherRepository.getCurrentLocation(params)
}