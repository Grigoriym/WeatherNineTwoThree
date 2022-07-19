package com.grappim.weatherninetwothree.di.app

import com.grappim.weatherninetwothree.data.dataSource.remote.WeatherRemoteDataSourceImpl
import com.grappim.weatherninetwothree.domain.dataSource.remote.WeatherRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface DataSourceModule {

    @Binds
    fun bindWeatherRemoteDataSource(
        weatherRemoteDataSourceImpl: WeatherRemoteDataSourceImpl
    ): WeatherRemoteDataSource

}