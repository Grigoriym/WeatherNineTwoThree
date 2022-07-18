package com.grappim.weatherninetwothree.data.repository

import com.grappim.weatherninetwothree.data.network.service.GeoapifyService
import com.grappim.weatherninetwothree.di.app.QualifierGeoapifyService
import com.grappim.weatherninetwothree.domain.interactor.search_location.SearchLocationParams
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.interactor.utils.runOperationCatching
import com.grappim.weatherninetwothree.domain.model.location.FoundLocation
import com.grappim.weatherninetwothree.domain.repository.GeocodingAndSearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeocodingAndSearchRepositoryImpl @Inject constructor(
    @QualifierGeoapifyService private val geoapifyService: GeoapifyService
) : GeocodingAndSearchRepository {

    override suspend fun searchLocation(
        params: SearchLocationParams
    ): Try<List<FoundLocation>, Throwable> = runOperationCatching {
        val result = geoapifyService.searchLocation(
            searchQuery = params.searchQuery
        )
        val found = mutableListOf<FoundLocation>()
        result.features
            .filter {
                it.properties.city?.isNotEmpty() == true
            }
            .forEach {
                found.add(
                    FoundLocation(
                        cityName = it.properties.city!!,
                        latitude = it.properties.lat,
                        longitude = it.properties.lon
                    )
                )
            }
        found
    }
}