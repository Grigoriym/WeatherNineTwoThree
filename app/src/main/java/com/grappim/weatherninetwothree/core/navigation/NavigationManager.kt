package com.grappim.weatherninetwothree.core.navigation

import android.os.Bundle

interface NavigationManager {

    fun goToDetails(args: Bundle?)

    fun onBackPressed()

}