package com.grappim.weatherninetwothree.ui.search_city

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grappim.weatherninetwothree.domain.FoundLocation
import com.grappim.weatherninetwothree.domain.interactor.GetCurrentPlaceUseCase
import com.grappim.weatherninetwothree.domain.interactor.SearchLocationUseCase
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.utils.INPUT_DEBOUNCE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val searchLocationUseCase: SearchLocationUseCase,
    private val getCurrentPlaceUseCase: GetCurrentPlaceUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _searchInput = MutableSharedFlow<String>()
    val searchInput: SharedFlow<Try<List<FoundLocation>>>
        get() = _searchInput.asSharedFlow()
            .filter { it.length >= MIN_EDIT_TEXT_INPUT_SIZE }
            .debounce(INPUT_DEBOUNCE)
            .flatMapMerge {
                searchLocationUseCase.invoke(SearchLocationUseCase.Params(it))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Try.Initial
            )

    var isButtonOkVisible: Boolean =
        savedStateHandle[SearchCityFragment.ARG_KEY_BTN_OK_VISIBILITY] ?: false

    var latitude: Double? = savedStateHandle[SearchCityFragment.ARG_KEY_LATITUDE]
    var longitude: Double? = savedStateHandle[SearchCityFragment.ARG_KEY_LATITUDE]

    private val _foundLocation = MutableSharedFlow<String>()
    val foundLocation: SharedFlow<String>
        get() = _foundLocation.asSharedFlow()

    fun getCurrentPlace() {
        viewModelScope.launch {
            getCurrentPlaceUseCase.invoke(
                GetCurrentPlaceUseCase.Params(
                    longitude = requireNotNull(longitude),
                    latitude = requireNotNull(latitude)
                )
            ).collect {
                when (it) {
                    is Try.Success -> {
                        _foundLocation.emit(it.data.name)
                    }
                }
            }
        }
    }

    fun searchLocation(query: String) {
        viewModelScope.launch {
            _searchInput.emit(query)
        }
    }

    companion object {
        /**
         * The api won't give us any useful data if input size < 3
         */
        private const val MIN_EDIT_TEXT_INPUT_SIZE = 3
    }
}