package com.grappim.weatherninetwothree.data.repository

import com.grappim.weatherninetwothree.domain.dataSource.remote.GeocodingAndSearchRemoteDataSource
import com.grappim.weatherninetwothree.domain.interactor.searchLocation.SearchLocationParams
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.interactor.utils.mapSuccess
import com.grappim.weatherninetwothree.domain.model.location.FoundLocation
import com.grappim.weatherninetwothree.domain.repository.GeocodingAndSearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeocodingAndSearchRepositoryImpl @Inject constructor(
    private val geocodingAndSearchRemoteDataSource: GeocodingAndSearchRemoteDataSource
) : GeocodingAndSearchRepository {

    override suspend fun searchLocation(
        params: SearchLocationParams
    ): Try<List<FoundLocation>, Throwable> =
        geocodingAndSearchRemoteDataSource
            .searchLocation(params)
            .mapSuccess { result ->
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