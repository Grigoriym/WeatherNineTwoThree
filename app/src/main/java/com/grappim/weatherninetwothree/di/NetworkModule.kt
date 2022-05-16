package com.grappim.weatherninetwothree.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.grappim.weatherninetwothree.BuildConfig
import com.grappim.weatherninetwothree.data.network.Tls12SocketFactory.Companion.enableTls12Compat
import com.grappim.weatherninetwothree.data.network.interceptors.ErrorMappingInterceptor
import com.grappim.weatherninetwothree.data.network.interceptors.GeoapifyApiKeyInterceptor
import com.grappim.weatherninetwothree.data.network.interceptors.OpenWeatherAuthTokenInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class QualifierWeatherRetrofit

@Qualifier
annotation class QualifierGeocodingAndSearchRetrofit

@[Module InstallIn(SingletonComponent::class)]
object NetworkModule {

    @[Provides Singleton]
    fun provideSerialization(): Json =
        Json {
            isLenient = true
            prettyPrint = false
            ignoreUnknownKeys = true
            explicitNulls = false
        }

    @[Provides Singleton]
    fun provideRetrofitBuilder(
        json: Json
    ): Retrofit.Builder {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
    }

    @[Provides Singleton QualifierWeatherRetrofit]
    fun provideWeatherRetrofit(
        builder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
        appBuildConfigProvider: AppBuildConfigProvider
    ): Retrofit =
        builder.baseUrl(appBuildConfigProvider.apiOpenWeatherHost)
            .client(okHttpClient)
            .build()

    @[Provides Singleton QualifierGeocodingAndSearchRetrofit]
    fun provideGeocodingAndSearchRetrofit(
        builder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
        appBuildConfigProvider: AppBuildConfigProvider
    ): Retrofit =
        builder.baseUrl(appBuildConfigProvider.geoApifyHost)
            .client(okHttpClient)
            .build()

    @[Provides Singleton]
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            Timber.tag("API").d(message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @[Provides Singleton]
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        errorMappingInterceptor: ErrorMappingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        openWeatherAuthTokenInterceptor: OpenWeatherAuthTokenInterceptor,
        geoapifyApiKeyInterceptor: GeoapifyApiKeyInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient =
        OkHttpClient.Builder()
            .enableTls12Compat(context)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(openWeatherAuthTokenInterceptor)
            .addInterceptor(geoapifyApiKeyInterceptor)
            .addInterceptor(errorMappingInterceptor)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(loggingInterceptor)
                    addInterceptor(chuckerInterceptor)
                }
            }
            .build()

    @[Singleton Provides]
    fun provideChuckerInterceptor(
        @ApplicationContext appContext: Context
    ): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = appContext,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        return ChuckerInterceptor.Builder(context = appContext)
            .collector(collector = chuckerCollector)
            .maxContentLength(
                length = 250000L
            )
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(true)
            .build()
    }
}