package com.grappim.weatherninetwothree.di

import com.grappim.weatherninetwothree.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @[Provides Singleton]
    fun provideAppBuildConfigProvider() =
        AppBuildConfigProvider(
            apiOpenWeatherHost = BuildConfig.API_OPEN_WEATHER,
            openWeatherIconHost = BuildConfig.OPEN_WEATHER_ICON,
            openWeatherApiKey = BuildConfig.OPEN_WEATHER_API_KEY,
            geoApifyHost = BuildConfig.GEOAPIFY_API_HOST,
            geoApifyKey = BuildConfig.GEOAPIFY_API_KEY
        )
}