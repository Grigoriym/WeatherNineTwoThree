package com.grappim.weatherninetwothree.domain.dataSource.local

import com.grappim.weatherninetwothree.domain.model.base.TemperatureUnit

interface LocalOptionsDataSource {

    fun saveTemperatureUnit(unit: TemperatureUnit)
    fun getTemperatureUnit(): TemperatureUnit
}