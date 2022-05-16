package com.grappim.weatherninetwothree.domain.repository

import com.grappim.weatherninetwothree.domain.FoundLocation
import com.grappim.weatherninetwothree.domain.interactor.SearchLocationUseCase
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import kotlinx.coroutines.flow.Flow

interface GeocodingAndSearchRepository {

    fun searchLocation(
        params: SearchLocationUseCase.Params
    ): Flow<Try<List<FoundLocation>>>

}