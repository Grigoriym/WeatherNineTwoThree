package com.grappim.weatherninetwothree.di.fragment

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Qualifier

@Qualifier
annotation class FragmentNavController

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @[Provides FragmentNavController]
    fun provideNavController(fragment: Fragment): NavController =
        fragment.findNavController()

}