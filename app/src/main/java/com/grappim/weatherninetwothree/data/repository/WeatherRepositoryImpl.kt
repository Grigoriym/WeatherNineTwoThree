package com.grappim.weatherninetwothree.data.repository

import com.grappim.weatherninetwothree.data.mapper.toDomain
import com.grappim.weatherninetwothree.data.network.service.WeatherService
import com.grappim.weatherninetwothree.di.app.AppBuildConfigProvider
import com.grappim.weatherninetwothree.di.app.DateTimeStandard
import com.grappim.weatherninetwothree.di.app.DecimalWholeNumberFormat
import com.grappim.weatherninetwothree.di.app.QualifierWeatherService
import com.grappim.weatherninetwothree.domain.interactor.weather_data.GetWeatherDataUseCaseImpl
import com.grappim.weatherninetwothree.domain.interactor.get_current_place.GetCurrentPlaceParams
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.interactor.utils.runOperationCatching
import com.grappim.weatherninetwothree.domain.interactor.weather_data.WeatherDataParams
import com.grappim.weatherninetwothree.domain.model.location.CurrentLocation
import com.grappim.weatherninetwothree.domain.model.weather.WeatherDetails
import com.grappim.weatherninetwothree.domain.repository.WeatherRepository
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

    override suspend fun getWeather(
        params: WeatherDataParams
    ): Try<WeatherDetails, Throwable> = runOperationCatching {
        val response = weatherService.getCurrentAndDailyWeather(
            latitude = params.latitude,
            longitude = params.longitude
        )
        val domain = response.toDomain(
            iconHost = appBuildConfigProvider.openWeatherIconHost,
            dfWholeNumber = dfWholeNumber,
            dtStandard = dtStandard
        )
        domain
    }

    override suspend fun getCurrentLocation(
        params: GetCurrentPlaceParams
    ): Try<CurrentLocation, Throwable> = runOperationCatching {
        val response = weatherService.getReverseGeocoding(
            latitude = params.latitude,
            longitude = params.longitude
        )
        response.first().toDomain()
    }

}