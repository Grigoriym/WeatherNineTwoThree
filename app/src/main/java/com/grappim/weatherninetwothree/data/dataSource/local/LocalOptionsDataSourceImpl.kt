package com.grappim.weatherninetwothree.data.dataSource.local

import android.content.Context
import com.grappim.weatherninetwothree.domain.dataSource.local.LocalOptionsDataSource
import com.grappim.weatherninetwothree.domain.model.base.Units
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalOptionsDataSourceImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : LocalOptionsDataSource {

    private companion object {
        private const val OPTIONS_PREFS_NAME = "options.prefs.name"

        private const val TEMPERATURE_UNIT = "temperature.unit"
    }

    private val sharedPrefs = appContext
        .getSharedPreferences(OPTIONS_PREFS_NAME, Context.MODE_PRIVATE)

    private val editPrefs = sharedPrefs.edit()

    override fun saveTemperatureUnit(unit: Units) {
        editPrefs.putString(TEMPERATURE_UNIT, unit.title).apply()
    }

    override fun getTemperatureUnit(): Units =
        Units.getUnit(
            sharedPrefs
                .getString(TEMPERATURE_UNIT, Units.getDefault().title)
                ?: throw IllegalArgumentException("no value for $TEMPERATURE_UNIT")
        )

}