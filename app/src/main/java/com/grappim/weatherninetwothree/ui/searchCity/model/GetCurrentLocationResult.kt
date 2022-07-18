package com.grappim.weatherninetwothree.ui.searchCity.model

import com.grappim.weatherninetwothree.domain.model.location.CurrentLocation

sealed class GetCurrentLocationResult {

    object Loading : GetCurrentLocationResult()
    data class SuccessResult(
        val result: CurrentLocation
    ) : GetCurrentLocationResult()

    data class ErrorResult(val e: Throwable) : GetCurrentLocationResult()
}