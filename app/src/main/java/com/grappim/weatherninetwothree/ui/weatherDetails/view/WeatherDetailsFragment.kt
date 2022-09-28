package com.grappim.weatherninetwothree.ui.weatherDetails.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.load
import com.grappim.weatherninetwothree.R
import com.grappim.weatherninetwothree.core.InsetterSetter
import com.grappim.weatherninetwothree.core.InsetterSetterDelegate
import com.grappim.weatherninetwothree.core.navigation.NavigationManager
import com.grappim.weatherninetwothree.databinding.FragmentWeatherDetailsBinding
import com.grappim.weatherninetwothree.domain.model.base.Units
import com.grappim.weatherninetwothree.ui.searchCity.model.CurrentLocationInfo
import com.grappim.weatherninetwothree.ui.weatherDetails.adapter.WeatherDetailsAdapter
import com.grappim.weatherninetwothree.ui.weatherDetails.viewModel.WeatherDataResult
import com.grappim.weatherninetwothree.ui.weatherDetails.viewModel.WeatherDetailsViewModel
import com.grappim.weatherninetwothree.utils.delegate.lazyArg
import com.grappim.weatherninetwothree.utils.extensions.assistedViewModel
import com.grappim.weatherninetwothree.utils.extensions.launchAndRepeatWithViewLifecycle
import com.grappim.weatherninetwothree.utils.extensions.viewBinding
import com.grappim.weatherninetwothree.utils.views.SimpleItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherDetailsFragment : Fragment(R.layout.fragment_weather_details),
    InsetterSetter by InsetterSetterDelegate() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var weatherDetailsViewModelFactory: WeatherDetailsViewModel.WeatherDetailsViewModelFactory

    private val binding by viewBinding(FragmentWeatherDetailsBinding::bind)

    private val weatherAdapter: WeatherDetailsAdapter by lazy {
        WeatherDetailsAdapter(viewModel.units)
    }
    private val currentLocationInfo by lazyArg<CurrentLocationInfo>(ARG_KEY_LOCATION_INFO)

    private val viewModel: WeatherDetailsViewModel by assistedViewModel {
        weatherDetailsViewModelFactory.create(currentLocationInfo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInsetterForSystemBars(binding.btnBack)
        setInsetterForNavigationBarsAndIme(binding.rvDetails, false)
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

                    tvWeatherType.text = result.currentWeather.description
                    ivWeatherType.load(result.currentWeather.icon)
                    swipeRefresh.isRefreshing = false

                    val currentTempString = when (viewModel.units) {
                        Units.IMPERIAL -> R.string.temp_f
                        Units.METRIC -> R.string.temp_c
                    }
                    tvCurrentTemp.text = getString(
                        currentTempString,
                        result.currentWeather.temp
                    )
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

//            rvDetails.applyInsetter { type(navigationBars = true) { padding() } }
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