package com.grappim.weatherninetwothree.di.app

import com.grappim.weatherninetwothree.domain.interactor.get_current_place.GetCurrentPlaceUseCase
import com.grappim.weatherninetwothree.domain.interactor.get_current_place.GetCurrentPlaceUseCaseImpl
import com.grappim.weatherninetwothree.domain.interactor.search_location.SearchLocationUseCase
import com.grappim.weatherninetwothree.domain.interactor.search_location.SearchLocationUseCaseImpl
import com.grappim.weatherninetwothree.domain.interactor.weather_data.GetWeatherDataUseCase
import com.grappim.weatherninetwothree.domain.interactor.weather_data.GetWeatherDataUseCaseImpl
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