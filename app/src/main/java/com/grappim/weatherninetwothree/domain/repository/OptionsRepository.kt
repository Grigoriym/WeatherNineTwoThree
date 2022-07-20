package com.grappim.weatherninetwothree.domain.repository

import com.grappim.weatherninetwothree.domain.model.base.TemperatureUnit

interface OptionsRepository {
    suspend fun saveTemperatureUnit(unit: TemperatureUnit)
    fun getTemperatureUnit(): TemperatureUnit
}