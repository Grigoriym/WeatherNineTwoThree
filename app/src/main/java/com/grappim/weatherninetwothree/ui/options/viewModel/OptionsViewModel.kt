package com.grappim.weatherninetwothree.ui.options.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grappim.weatherninetwothree.domain.interactor.options.OptionsUseCase
import com.grappim.weatherninetwothree.domain.model.base.TemperatureUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OptionsViewModel @Inject constructor(
    private val optionsUseCase: OptionsUseCase
) : ViewModel() {

    val currentTemperatureUnit: TemperatureUnit
        get() = optionsUseCase.getTemperatureUnit()

    fun saveTemperatureUnit(unit: TemperatureUnit) {
        viewModelScope.launch {
            optionsUseCase.saveTemperatureUnit(unit)
        }
    }
}