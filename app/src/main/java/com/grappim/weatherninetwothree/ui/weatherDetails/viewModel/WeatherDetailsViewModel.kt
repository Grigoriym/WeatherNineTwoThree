package com.grappim.weatherninetwothree.ui.weatherDetails.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grappim.weatherninetwothree.domain.interactor.options.OptionsUseCase
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.interactor.weatherData.GetWeatherDataUseCase
import com.grappim.weatherninetwothree.domain.interactor.weatherData.WeatherDataParams
import com.grappim.weatherninetwothree.domain.model.base.TemperatureUnit
import com.grappim.weatherninetwothree.ui.searchCity.model.CurrentLocationInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class WeatherDetailsViewModel @AssistedInject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    @Assisted private val currentLocationInfo: CurrentLocationInfo,
    private val optionsUseCase: OptionsUseCase
) : ViewModel() {

    val temperatureUnit: TemperatureUnit
        get() = optionsUseCase.getTemperatureUnit()

    private val _weatherDetails = MutableSharedFlow<WeatherDataResult>(
        replay = 1
    )
    val weatherDetails: SharedFlow<WeatherDataResult>
        get() = _weatherDetails.asSharedFlow()

    init {
        getWeatherData()
    }

    fun getWeatherData() {
        viewModelScope.launch {
            _weatherDetails.emit(WeatherDataResult.Loading)
            val weatherResult = getWeatherDataUseCase.invoke(
                WeatherDataParams(
                    latitude = currentLocationInfo.latitude,
                    longitude = currentLocationInfo.longitude
                )
            )
            when (weatherResult) {
                is Try.Success -> {
                    _weatherDetails.emit(WeatherDataResult.SuccessResult(weatherResult.result))
                }
                is Try.Error -> {
                    _weatherDetails.emit(WeatherDataResult.ErrorResult(weatherResult.result))
                }
            }
        }
    }

    @AssistedFactory
    interface WeatherDetailsViewModelFactory {
        fun create(currentLocationInfo: CurrentLocationInfo): WeatherDetailsViewModel
    }

}