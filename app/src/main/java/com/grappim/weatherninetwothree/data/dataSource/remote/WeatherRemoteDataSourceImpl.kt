package com.grappim.weatherninetwothree.data.dataSource.remote

import com.grappim.weatherninetwothree.data.model.geocoding.CurrentLocationDTO
import com.grappim.weatherninetwothree.data.model.weather.WeatherCurrentDailyDTO
import com.grappim.weatherninetwothree.data.network.service.WeatherService
import com.grappim.weatherninetwothree.di.app.QualifierWeatherService
import com.grappim.weatherninetwothree.domain.dataSource.remote.WeatherRemoteDataSource
import com.grappim.weatherninetwothree.domain.interactor.getCurrentPlace.GetCurrentPlaceParams
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.interactor.utils.runOperationCatching
import com.grappim.weatherninetwothree.domain.interactor.weatherData.WeatherDataParams
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRemoteDataSourceImpl @Inject constructor(
    @QualifierWeatherService private val weatherService: WeatherService,
) : WeatherRemoteDataSource {

    override suspend fun getWeather(
        params: WeatherDataParams
    ): Try<WeatherCurrentDailyDTO, Throwable> = runOperationCatching {
        weatherService.getCurrentAndDailyWeather(
            latitude = params.latitude,
            longitude = params.longitude
        )
    }

    override suspend fun getCurrentLocation(
        params: GetCurrentPlaceParams
    ): Try<List<CurrentLocationDTO>, Throwable> = runOperationCatching {
        weatherService.getReverseGeocoding(
            latitude = params.latitude,
            longitude = params.longitude
        )
    }
}