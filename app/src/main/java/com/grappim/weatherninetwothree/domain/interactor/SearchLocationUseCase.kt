package com.grappim.weatherninetwothree.domain.interactor

import com.grappim.weatherninetwothree.di.app.IoDispatcher
import com.grappim.weatherninetwothree.domain.interactor.base.FlowUseCase
import com.grappim.weatherninetwothree.domain.model.location.FoundLocation
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.repository.GeocodingAndSearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchLocationUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val geocodingAndSearchRepository: GeocodingAndSearchRepository
) : FlowUseCase<SearchLocationUseCase.Params, List<FoundLocation>>(ioDispatcher) {

    data class Params(
        val searchQuery: String
    )

    override fun execute(params: Params): Flow<Try<List<FoundLocation>>> =
        geocodingAndSearchRepository.searchLocation(params)
}