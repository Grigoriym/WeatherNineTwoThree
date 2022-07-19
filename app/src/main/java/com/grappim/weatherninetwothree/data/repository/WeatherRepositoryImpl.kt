package com.grappim.weatherninetwothree.data.repository

import com.grappim.weatherninetwothree.data.mapper.toDomain
import com.grappim.weatherninetwothree.di.app.AppBuildConfigProvider
import com.grappim.weatherninetwothree.di.app.DateTimeStandard
import com.grappim.weatherninetwothree.di.app.DecimalWholeNumberFormat
import com.grappim.weatherninetwothree.domain.dataSource.remote.WeatherRemoteDataSource
import com.grappim.weatherninetwothree.domain.interactor.getCurrentPlace.GetCurrentPlaceParams
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.interactor.utils.mapSuccess
import com.grappim.weatherninetwothree.domain.interactor.weatherData.WeatherDataParams
import com.grappim.weatherninetwothree.domain.model.location.CurrentLocation
import com.grappim.weatherninetwothree.domain.model.weather.WeatherDetails
import com.grappim.weatherninetwothree.domain.repository.WeatherRepository
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val appBuildConfigProvider: AppBuildConfigProvider,
    @DecimalWholeNumberFormat private val dfWholeNumber: DecimalFormat,
    @DateTimeStandard private val dtStandard: DateTimeFormatter,
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {

    override suspend fun getWeather(
        params: WeatherDataParams
    ): Try<WeatherDetails, Throwable> =
        weatherRemoteDataSource
            .getWeather(params)
            .mapSuccess { response ->
                response.toDomain(
                    iconHost = appBuildConfigProvider.openWeatherIconHost,
                    dfWholeNumber = dfWholeNumber,
                    dtStandard = dtStandard
                )
            }

    override suspend fun getCurrentLocation(
        params: GetCurrentPlaceParams
    ): Try<CurrentLocation, Throwable> =
        weatherRemoteDataSource
            .getCurrentLocation(params)
            .mapSuccess { response ->
                response.first().toDomain()
            }

}