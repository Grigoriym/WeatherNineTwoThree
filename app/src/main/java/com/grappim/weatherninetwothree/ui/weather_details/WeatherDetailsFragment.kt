package com.grappim.weatherninetwothree.ui.weather_details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.grappim.weatherninetwothree.R
import com.grappim.weatherninetwothree.core.base.BaseFragment
import com.grappim.weatherninetwothree.databinding.FragmentWeatherDetailsBinding
import com.grappim.weatherninetwothree.ui.search_city.CurrentLocationInfo
import com.grappim.weatherninetwothree.utils.delegate.lazyArg
import com.grappim.weatherninetwothree.utils.extensions.assistedViewModel
import com.grappim.weatherninetwothree.utils.views.SimpleItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
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

    private val viewModel by assistedViewModel<WeatherDetailsViewModel> {
        weatherDetailsViewModelFactory.create(currentLocationInfo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weatherDetails
                .filterNotNull()
                .collect {
                    weatherAdapter.submitList(it.daily)
                    with(binding) {
                        tvCity.text = currentLocationInfo.name
                        tvDate.text = it.currentWeather.currentTime
                        tvCurrentTemp.text = getString(
                            R.string.temp,
                            it.currentWeather.temp
                        )
                        tvWeatherType.text = it.currentWeather.description
                        ivWeatherType.load(it.currentWeather.icon)
                    }
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
                findNavController().navigateUp()
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