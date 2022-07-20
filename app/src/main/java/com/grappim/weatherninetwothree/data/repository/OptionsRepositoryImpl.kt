package com.grappim.weatherninetwothree.data.repository

import com.grappim.weatherninetwothree.di.app.IoDispatcher
import com.grappim.weatherninetwothree.domain.dataSource.local.LocalOptionsDataSource
import com.grappim.weatherninetwothree.domain.model.base.TemperatureUnit
import com.grappim.weatherninetwothree.domain.repository.OptionsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OptionsRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val localOptionsDataSource: LocalOptionsDataSource
) : OptionsRepository {

    override suspend fun saveTemperatureUnit(unit: TemperatureUnit) =
        withContext(ioDispatcher) {
            localOptionsDataSource.saveTemperatureUnit(unit)
        }

    override fun getTemperatureUnit(): TemperatureUnit =
        localOptionsDataSource.getTemperatureUnit()

}