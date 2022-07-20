package com.grappim.weatherninetwothree.domain.interactor.options

import com.grappim.weatherninetwothree.domain.model.base.Units
import com.grappim.weatherninetwothree.domain.repository.OptionsRepository
import javax.inject.Inject

class OptionsUseCase @Inject constructor(
    private val optionsRepository: OptionsRepository
) {

    suspend fun saveTemperatureUnit(unit: Units) {
        optionsRepository.saveTemperatureUnit(unit)
    }

    fun getTemperatureUnit(): Units =
        optionsRepository.getTemperatureUnit()

}