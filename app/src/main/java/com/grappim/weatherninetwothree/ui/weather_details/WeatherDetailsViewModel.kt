package com.grappim.weatherninetwothree.ui.weather_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grappim.weatherninetwothree.domain.interactor.GetWeatherDataUseCase
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.weather.WeatherDetails
import com.grappim.weatherninetwothree.ui.search_city.CurrentLocationInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherDetailsViewModel @AssistedInject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    @Assisted private val currentLocationInfo: CurrentLocationInfo
) : ViewModel() {

    private val _weatherDetails = MutableStateFlow<WeatherDetails?>(null)
    val weatherDetails: StateFlow<WeatherDetails?>
        get() = _weatherDetails.asStateFlow()

    init {
        getWeatherData()
    }

    fun getWeatherData() {
        viewModelScope.launch {
            getWeatherDataUseCase.invoke(
                GetWeatherDataUseCase.Params(
                    latitude = currentLocationInfo.latitude,
                    longitude = currentLocationInfo.longitude
                )
            ).collect {
                when (it) {
                    is Try.Success -> {
                        _weatherDetails.emit(it.data)
                    }
                }
            }
        }
    }

    @AssistedFactory
    interface WeatherDetailsViewModelFactory {
        fun create(currentLocationInfo: CurrentLocationInfo): WeatherDetailsViewModel
    }

}