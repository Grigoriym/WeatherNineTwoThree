package com.grappim.weatherninetwothree.domain.interactor.getCurrentPlace

import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.location.CurrentLocation

interface GetCurrentPlaceUseCase {
    suspend operator fun invoke(
        params: GetCurrentPlaceParams
    ): Try<CurrentLocation, Throwable>
}