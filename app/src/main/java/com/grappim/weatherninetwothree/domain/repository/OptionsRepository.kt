package com.grappim.weatherninetwothree.domain.repository

import com.grappim.weatherninetwothree.domain.model.base.Units

interface OptionsRepository {
    suspend fun saveTemperatureUnit(unit: Units)
    fun getTemperatureUnit(): Units
}