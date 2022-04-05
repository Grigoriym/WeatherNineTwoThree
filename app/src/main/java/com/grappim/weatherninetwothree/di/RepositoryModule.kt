package com.grappim.weatherninetwothree.di

import com.grappim.weatherninetwothree.data.repository.GeocodingAndSearchRepositoryImpl
import com.grappim.weatherninetwothree.data.repository.OauthRepositoryImpl
import com.grappim.weatherninetwothree.data.repository.WeatherRepositoryImpl
import com.grappim.weatherninetwothree.domain.repository.GeocodingAndSearchRepository
import com.grappim.weatherninetwothree.domain.repository.OauthRepository
import com.grappim.weatherninetwothree.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindOauthRepository(
        oauthRepositoryImpl: OauthRepositoryImpl
    ): OauthRepository

    @Binds
    fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    fun bindGeocodingAndSearchRepository(
        geocodingAndSearchRepositoryImpl: GeocodingAndSearchRepositoryImpl
    ): GeocodingAndSearchRepository

}