package com.grappim.weatherninetwothree.domain.interactor.options

import com.grappim.weatherninetwothree.domain.model.base.TemperatureUnit
import com.grappim.weatherninetwothree.domain.repository.OptionsRepository
import javax.inject.Inject

class OptionsUseCase @Inject constructor(
    private val optionsRepository: OptionsRepository
) {

    suspend fun saveTemperatureUnit(unit: TemperatureUnit) {
        optionsRepository.saveTemperatureUnit(unit)
    }

    fun getTemperatureUnit(): TemperatureUnit =
        optionsRepository.getTemperatureUnit()

}