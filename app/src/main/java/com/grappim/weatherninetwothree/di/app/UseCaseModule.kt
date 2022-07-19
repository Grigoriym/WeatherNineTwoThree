package com.grappim.weatherninetwothree.di.app

import com.grappim.weatherninetwothree.domain.interactor.getCurrentPlace.GetCurrentPlaceUseCase
import com.grappim.weatherninetwothree.domain.interactor.getCurrentPlace.GetCurrentPlaceUseCaseImpl
import com.grappim.weatherninetwothree.domain.interactor.searchLocation.SearchLocationUseCase
import com.grappim.weatherninetwothree.domain.interactor.searchLocation.SearchLocationUseCaseImpl
import com.grappim.weatherninetwothree.domain.interactor.weatherData.GetWeatherDataUseCase
import com.grappim.weatherninetwothree.domain.interactor.weatherData.GetWeatherDataUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindSearchLocationUseCase(
        searchLocationUseCaseImpl: SearchLocationUseCaseImpl
    ): SearchLocationUseCase

    @Binds
    fun bindGetCurrentPlaceUseCase(
        getCurrentPlaceUseCase: GetCurrentPlaceUseCaseImpl
    ): GetCurrentPlaceUseCase

    @Binds
    fun bindGetWeatherDataUseCase(
        getWeatherDataUseCaseImpl: GetWeatherDataUseCaseImpl
    ): GetWeatherDataUseCase

}