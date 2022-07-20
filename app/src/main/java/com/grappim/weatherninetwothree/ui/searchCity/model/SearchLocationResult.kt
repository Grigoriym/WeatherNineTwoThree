package com.grappim.weatherninetwothree.ui.searchCity.model

import com.grappim.weatherninetwothree.domain.model.location.FoundLocation

sealed class SearchLocationResult {
    object Loading : SearchLocationResult()
    data class SuccessResult(
        val result: List<FoundLocation>
    ) : SearchLocationResult()

    data class ErrorResult(val result: Throwable) : SearchLocationResult()
}