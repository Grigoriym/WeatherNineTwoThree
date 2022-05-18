package com.grappim.weatherninetwothree.di.app

import com.grappim.weatherninetwothree.data.network.service.GeoapifyService
import com.grappim.weatherninetwothree.data.network.service.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class QualifierWeatherService

@Qualifier
annotation class QualifierGeoapifyService

@[Module InstallIn(SingletonComponent::class)]
object ApiModule {

    @[Singleton Provides QualifierWeatherService]
    fun provideWeatherService(
        @QualifierWeatherRetrofit retrofit: Retrofit
    ): WeatherService = retrofit.create(WeatherService::class.java)

    @[Singleton Provides QualifierGeoapifyService]
    fun provideGeoapifyService(
        @QualifierGeocodingAndSearchRetrofit retrofit: Retrofit
    ): GeoapifyService = retrofit.create(GeoapifyService::class.java)
}