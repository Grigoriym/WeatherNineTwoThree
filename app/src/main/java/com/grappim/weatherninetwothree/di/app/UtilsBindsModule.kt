package com.grappim.weatherninetwothree.di.app

import com.grappim.weatherninetwothree.core.location.LastKnowLocationManager
import com.grappim.weatherninetwothree.core.location.LastKnowLocationManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface UtilsBindsModule {

    @Binds
    fun bindsLastKnownLocationManager(
        lastKnowLocationManagerImpl: LastKnowLocationManagerImpl
    ): LastKnowLocationManager

}