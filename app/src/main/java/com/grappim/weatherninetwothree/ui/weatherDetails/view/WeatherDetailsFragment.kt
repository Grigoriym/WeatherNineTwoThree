package com.grappim.weatherninetwothree.ui.weatherDetails.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import coil.load
import com.grappim.weatherninetwothree.R
import com.grappim.weatherninetwothree.core.base.BaseFragment
import com.grappim.weatherninetwothree.databinding.FragmentWeatherDetailsBinding
import com.grappim.weatherninetwothree.ui.searchCity.model.CurrentLocationInfo
import com.grappim.weatherninetwothree.ui.weatherDetails.adapter.WeatherDetailsAdapter
import com.grappim.weatherninetwothree.ui.weatherDetails.viewModel.WeatherDataResult
import com.grappim.weatherninetwothree.ui.weatherDetails.viewModel.WeatherDetailsViewModel
import com.grappim.weatherninetwothree.utils.delegate.lazyArg
import com.grappim.weatherninetwothree.utils.extensions.assistedViewModel
import com.grappim.weatherninetwothree.utils.extensions.launchAndRepeatWithViewLifecycle
import com.grappim.weatherninetwothree.utils.views.SimpleItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherDetailsFragment : BaseFragment<FragmentWeatherDetailsBinding>(
    FragmentWeatherDetailsBinding::inflate
) {

    @Inject
    lateinit var weatherDetailsViewModelFactory: WeatherDetailsViewModel.WeatherDetailsViewModelFactory

    private val weatherAdapter: WeatherDetailsAdapter by lazy {
        WeatherDetailsAdapter()
    }
    private val currentLocationInfo by lazyArg<CurrentLocationInfo>(ARG_KEY_LOCATION_INFO)

    private val viewModel by assistedViewModel {
        weatherDetailsViewModelFactory.create(currentLocationInfo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        launchAndRepeatWithViewLifecycle {
            viewModel.weatherDetails.collect(::handleWeatherDataResult)
        }
    }

    private fun handleWeatherDataResult(state: WeatherDataResult) {
        when (state) {
            is WeatherDataResult.SuccessResult -> {
                val result = state.result
                weatherAdapter.submitList(result.daily)
                with(binding) {
                    tvCity.text = currentLocationInfo.name
                    tvDate.text = result.currentWeather.currentTime
                    tvCurrentTemp.text = getString(
                        R.string.temp,
                        result.currentWeather.temp
                    )
                    tvWeatherType.text = result.currentWeather.description
                    ivWeatherType.load(result.currentWeather.icon)
                    swipeRefresh.isRefreshing = false
                }
            }
            is WeatherDataResult.ErrorResult -> {

            }
            is WeatherDataResult.Loading -> {

            }
        }
    }

    private fun initViews() {
        with(binding) {
            rvDetails.adapter = weatherAdapter
            rvDetails.addItemDecoration(
                SimpleItemDecoration(context = requireContext())
            )
            btnBack.setOnClickListener {
                navigationManager.onBackPressed()
            }
            swipeRefresh.setOnRefreshListener {
                viewModel.getWeatherData()
            }
        }
    }

    companion object {
        private const val ARG_KEY_LOCATION_INFO = "arg.key.location.info"

        fun getBundleForDetails(
            name: String,
            longitude: Double,
            latitude: Double
        ): Bundle = bundleOf(
            ARG_KEY_LOCATION_INFO to CurrentLocationInfo(
                name = name,
                longitude = longitude,
                latitude = latitude
            )
        )
    }
}