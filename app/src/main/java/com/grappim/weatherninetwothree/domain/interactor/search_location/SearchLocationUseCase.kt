package com.grappim.weatherninetwothree.domain.interactor.search_location

import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.location.FoundLocation

interface SearchLocationUseCase {
    suspend operator fun invoke(
        params: SearchLocationParams
    ): Try<List<FoundLocation>, Throwable>
}