package com.grappim.weatherninetwothree.domain.interactor.searchLocation

import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.location.FoundLocation
import com.grappim.weatherninetwothree.domain.repository.GeocodingAndSearchRepository
import javax.inject.Inject

class SearchLocationUseCase @Inject constructor(
    private val geocodingAndSearchRepository: GeocodingAndSearchRepository
) {

    suspend fun invoke(
        params: SearchLocationParams
    ): Try<List<FoundLocation>, Throwable> =
        geocodingAndSearchRepository.searchLocation(params)
}