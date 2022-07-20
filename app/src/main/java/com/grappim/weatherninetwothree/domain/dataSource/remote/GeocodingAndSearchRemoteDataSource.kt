package com.grappim.weatherninetwothree.domain.dataSource.remote

import com.grappim.weatherninetwothree.data.model.geocoding.AutocompleteResponseDTO
import com.grappim.weatherninetwothree.domain.interactor.searchLocation.SearchLocationParams
import com.grappim.weatherninetwothree.domain.interactor.utils.Try

interface GeocodingAndSearchRemoteDataSource {
    suspend fun searchLocation(
        params: SearchLocationParams
    ): Try<AutocompleteResponseDTO, Throwable>
}