package com.grappim.weatherninetwothree.di.app

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class DecimalWholeNumberFormat

@Module
@InstallIn(SingletonComponent::class)
object DecimalFormatModule {

    private const val PATTERN_WHOLE_NUMBER = "###"

    @[Provides Singleton]
    fun provideDecimalFormatSymbols(): DecimalFormatSymbols =
        DecimalFormatSymbols()

    @[Provides Singleton DecimalWholeNumberFormat]
    fun provideSimpleDecimal(
        decimalFormatSymbols: DecimalFormatSymbols
    ): DecimalFormat {
        return DecimalFormat(PATTERN_WHOLE_NUMBER, decimalFormatSymbols)
    }
}