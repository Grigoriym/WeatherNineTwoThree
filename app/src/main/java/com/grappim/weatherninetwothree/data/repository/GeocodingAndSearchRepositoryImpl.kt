package com.grappim.weatherninetwothree.data.repository

import com.grappim.weatherninetwothree.data.network.service.GeoapifyService
import com.grappim.weatherninetwothree.di.app.QualifierGeoapifyService
import com.grappim.weatherninetwothree.domain.model.location.FoundLocation
import com.grappim.weatherninetwothree.domain.interactor.SearchLocationUseCase
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.repository.GeocodingAndSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeocodingAndSearchRepositoryImpl @Inject constructor(
    @QualifierGeoapifyService private val geoapifyService: GeoapifyService
) : GeocodingAndSearchRepository {

    override fun searchLocation(
        params: SearchLocationUseCase.Params
    ): Flow<Try<List<FoundLocation>>> = flow {
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
        emit(Try.Success(found))
    }

}