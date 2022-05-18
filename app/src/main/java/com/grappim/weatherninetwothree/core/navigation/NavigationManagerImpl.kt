package com.grappim.weatherninetwothree.core.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.grappim.weatherninetwothree.R
import com.grappim.weatherninetwothree.di.fragment.FragmentNavController
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class NavigationManagerImpl @Inject constructor(
    @FragmentNavController private val navController: NavController
) : NavigationManager {

    override fun onBackPressed() {
        navController.navigateUp()
    }

    override fun goToDetails(args: Bundle?) {
        val navOptions = NavOptions.Builder()
            .apply {
                setEnterAnim(R.anim.enter_from_right)
                setExitAnim(R.anim.exit_to_left)
                setPopEnterAnim(R.anim.pop_enter_from_left)
                setPopExitAnim(R.anim.pop_exit_from_right)
            }
            .build()
        navController.navigate(
            R.id.action_fragmentSearchCity_to_fragmentWeatherDetails,
            args,
            navOptions
        )

    }

}