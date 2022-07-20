package com.grappim.weatherninetwothree.domain.dataSource.local

import com.grappim.weatherninetwothree.domain.model.base.Units

interface LocalOptionsDataSource {

    fun saveTemperatureUnit(unit: Units)
    fun getTemperatureUnit(): Units
}