package com.grappim.weatherninetwothree.data.repository

import com.grappim.weatherninetwothree.data.mapper.toDomain
import com.grappim.weatherninetwothree.data.network.service.WeatherService
import com.grappim.weatherninetwothree.di.app.AppBuildConfigProvider
import com.grappim.weatherninetwothree.di.app.DateTimeStandard
import com.grappim.weatherninetwothree.di.app.DecimalWholeNumberFormat
import com.grappim.weatherninetwothree.di.app.QualifierWeatherService
import com.grappim.weatherninetwothree.domain.interactor.GetCurrentPlaceUseCase
import com.grappim.weatherninetwothree.domain.interactor.GetWeatherDataUseCase
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.location.CurrentLocation
import com.grappim.weatherninetwothree.domain.model.weather.WeatherDetails
import com.grappim.weatherninetwothree.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    @QualifierWeatherService private val weatherService: WeatherService,
    private val appBuildConfigProvider: AppBuildConfigProvider,
    @DecimalWholeNumberFormat private val dfWholeNumber: DecimalFormat,
    @DateTimeStandard private val dtStandard: DateTimeFormatter
) : WeatherRepository {

    override fun getWeather(
        params: GetWeatherDataUseCase.Params
    ): Flow<Try<WeatherDetails>> = flow {
        emit(Try.Loading)
        val response = weatherService.getCurrentAndDailyWeather(
            latitude = params.latitude,
            longitude = params.longitude
        )
        val domain = response.toDomain(
            iconHost = appBuildConfigProvider.openWeatherIconHost,
            dfWholeNumber = dfWholeNumber,
            dtStandard = dtStandard
        )
        emit(Try.Success(domain))
    }

    override fun getCurrentLocation(
        params: GetCurrentPlaceUseCase.Params
    ): Flow<Try<CurrentLocation>> = flow {
        emit(Try.Loading)
        val response = weatherService.getReverseGeocoding(
            latitude = params.latitude,
            longitude = params.longitude
        )
        emit(Try.Success(response.first().toDomain()))
    }

}