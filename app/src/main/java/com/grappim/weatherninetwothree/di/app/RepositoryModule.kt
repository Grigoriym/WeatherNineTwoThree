package com.grappim.weatherninetwothree.di.app

import com.grappim.weatherninetwothree.data.repository.GeocodingAndSearchRepositoryImpl
import com.grappim.weatherninetwothree.data.repository.WeatherRepositoryImpl
import com.grappim.weatherninetwothree.domain.repository.GeocodingAndSearchRepository
import com.grappim.weatherninetwothree.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface RepositoryModule {

    @Binds
    fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    fun bindGeocodingAndSearchRepository(
        geocodingAndSearchRepositoryImpl: GeocodingAndSearchRepositoryImpl
    ): GeocodingAndSearchRepository

}