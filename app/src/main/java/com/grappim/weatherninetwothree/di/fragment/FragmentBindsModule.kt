package com.grappim.weatherninetwothree.di.fragment

import com.grappim.weatherninetwothree.core.navigation.NavigationManager
import com.grappim.weatherninetwothree.core.navigation.NavigationManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface FragmentBindsModule {

    @Binds
    fun bindNavigationManager(
        navigationManagerImpl: NavigationManagerImpl
    ): NavigationManager
}