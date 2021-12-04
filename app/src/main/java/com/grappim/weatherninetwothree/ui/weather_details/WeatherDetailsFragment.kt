package com.grappim.weatherninetwothree.ui.weather_details

import android.os.Bundle
import android.view.View
import com.grappim.weatherninetwothree.core.base.BaseFragment
import com.grappim.weatherninetwothree.databinding.FragmentWeatherDetailsBinding

class WeatherDetailsFragment : BaseFragment<FragmentWeatherDetailsBinding>(
    FragmentWeatherDetailsBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {
        with(binding) {

        }
    }
}