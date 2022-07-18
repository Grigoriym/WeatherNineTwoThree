package com.grappim.weatherninetwothree.domain.repository

import com.grappim.weatherninetwothree.domain.interactor.search_location.SearchLocationParams
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.location.FoundLocation

interface GeocodingAndSearchRepository {

    suspend fun searchLocation(
        params: SearchLocationParams
    ): Try<List<FoundLocation>, Throwable>

}