package com.grappim.weatherninetwothree.domain.interactor.search_location

import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.location.FoundLocation
import com.grappim.weatherninetwothree.domain.repository.GeocodingAndSearchRepository
import javax.inject.Inject

class SearchLocationUseCaseImpl @Inject constructor(
    private val geocodingAndSearchRepository: GeocodingAndSearchRepository
) : SearchLocationUseCase {

    override suspend fun invoke(
        params: SearchLocationParams
    ): Try<List<FoundLocation>, Throwable> =
        geocodingAndSearchRepository.searchLocation(params)
}