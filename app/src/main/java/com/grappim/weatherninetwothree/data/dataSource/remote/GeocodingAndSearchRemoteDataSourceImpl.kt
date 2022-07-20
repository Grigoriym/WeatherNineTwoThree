package com.grappim.weatherninetwothree.data.dataSource.remote

import com.grappim.weatherninetwothree.data.model.geocoding.AutocompleteResponseDTO
import com.grappim.weatherninetwothree.data.network.service.GeoapifyService
import com.grappim.weatherninetwothree.di.app.QualifierGeoapifyService
import com.grappim.weatherninetwothree.domain.dataSource.remote.GeocodingAndSearchRemoteDataSource
import com.grappim.weatherninetwothree.domain.interactor.searchLocation.SearchLocationParams
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.interactor.utils.runOperationCatching
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeocodingAndSearchRemoteDataSourceImpl @Inject constructor(
    @QualifierGeoapifyService private val geoapifyService: GeoapifyService
) : GeocodingAndSearchRemoteDataSource {
    override suspend fun searchLocation(
        params: SearchLocationParams
    ): Try<AutocompleteResponseDTO, Throwable> = runOperationCatching {
        geoapifyService.searchLocation(
            searchQuery = params.searchQuery
        )
    }
}