package com.grappim.weatherninetwothree.di.app

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.format.DateTimeFormatter
import javax.inject.Qualifier

@Qualifier
annotation class DateTimeStandard

@Module
@InstallIn(SingletonComponent::class)
object DateTimeFormatModule {

    private const val DATE_TIME_PATTERN_STANDARD = "MMM dd, EEE"

    @[Provides DateTimeStandard]
    fun provideDateTimeStandard(): DateTimeFormatter =
        DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_STANDARD)

}