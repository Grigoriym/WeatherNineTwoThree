package com.grappim.weatherninetwothree.di.app

import android.content.Context
import coil.ImageLoader
import coil.util.DebugLogger
import com.grappim.weatherninetwothree.BuildConfig
import com.grappim.weatherninetwothree.data.network.tls.Tls12SocketFactory.Companion.enableTls12Compat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {

    @[Provides Singleton]
    fun provideImageLoader(
        @ApplicationContext context: Context
    ): ImageLoader =
        ImageLoader.Builder(context)
            .apply {
                if (BuildConfig.DEBUG) {
                    logger(DebugLogger())
                }
                okHttpClient {
                    OkHttpClient.Builder()
                        .enableTls12Compat(context)
                        .build()
                }
            }
            .crossfade(true)
            .build()
}