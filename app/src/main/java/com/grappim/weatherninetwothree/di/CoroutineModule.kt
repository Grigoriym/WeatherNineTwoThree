package com.grappim.weatherninetwothree.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainImmediateDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @[Provides DefaultDispatcher]
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @[Provides IoDispatcher]
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @[Provides MainDispatcher]
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @[Provides MainImmediateDispatcher]
    fun providesMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}