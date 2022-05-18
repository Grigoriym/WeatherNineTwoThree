package com.grappim.weatherninetwothree.di.activity

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.grappim.weatherninetwothree.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideNavController(activity: Activity): NavController =
        activity.findNavController(R.id.navHostFragment)

}