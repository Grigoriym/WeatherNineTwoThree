package com.grappim.weatherninetwothree.di

import com.grappim.weatherninetwothree.data.network.service.GeocodingAndSearchService
import com.grappim.weatherninetwothree.data.network.service.OauthService
import com.grappim.weatherninetwothree.data.network.service.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class QualifierOauthService

@Qualifier
annotation class QualifierWeatherService

@Qualifier
annotation class QualifierGeocodingAndSearchService

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @QualifierOauthService
    @Singleton
    @Provides
    fun provideOauthService(
        @QualifierOauthRetrofit retrofit: Retrofit
    ): OauthService = retrofit.create(OauthService::class.java)

    @QualifierWeatherService
    @Singleton
    @Provides
    fun provideWeatherService(
        @QualifierWeatherRetrofit retrofit: Retrofit
    ): WeatherService = retrofit.create(WeatherService::class.java)

    @QualifierGeocodingAndSearchService
    @Singleton
    @Provides
    fun provideGeocodingAndSearchService(
        @QualifierGeocodingAndSearchRetrofit retrofit: Retrofit
    ): GeocodingAndSearchService = retrofit.create(GeocodingAndSearchService::class.java)
}