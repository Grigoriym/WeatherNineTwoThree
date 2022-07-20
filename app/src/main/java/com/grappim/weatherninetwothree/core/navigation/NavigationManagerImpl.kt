package com.grappim.weatherninetwothree.core.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.grappim.weatherninetwothree.R
import com.grappim.weatherninetwothree.di.fragment.FragmentNavController
import com.grappim.weatherninetwothree.ui.options.view.OptionsDialogFragment
import com.grappim.weatherninetwothree.ui.searchCity.view.SearchCityFragment
import com.grappim.weatherninetwothree.ui.weatherDetails.view.WeatherDetailsFragment
import dagger.hilt.android.scopes.ActivityScoped
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
                addDefaultAnimations()
            }
            .build()
        navController.navigate(
            R.id.action_fragmentSearchCity_to_fragmentWeatherDetails,
            args,
            navOptions
        )
    }

    override fun goToOptionsFromSearch() {
        val navOptions = NavOptions.Builder()
            .apply {
                addDefaultAnimations()
            }
            .build()
        navController.navigate(
            R.id.action_fragmentSearchCity_to_fragmentOptions
        )
    }

    private fun NavOptions.Builder.addDefaultAnimations() {
        setEnterAnim(R.anim.enter_from_right)
        setExitAnim(R.anim.exit_to_left)
        setPopEnterAnim(R.anim.pop_enter_from_left)
        setPopExitAnim(R.anim.pop_exit_from_right)
    }

}