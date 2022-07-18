package com.grappim.weatherninetwothree.ui.searchCity.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grappim.weatherninetwothree.domain.interactor.get_current_place.GetCurrentPlaceParams
import com.grappim.weatherninetwothree.domain.interactor.get_current_place.GetCurrentPlaceUseCase
import com.grappim.weatherninetwothree.domain.interactor.search_location.SearchLocationParams
import com.grappim.weatherninetwothree.domain.interactor.search_location.SearchLocationUseCase
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.ui.searchCity.model.GetCurrentLocationResult
import com.grappim.weatherninetwothree.ui.searchCity.model.SearchLocationResult
import com.grappim.weatherninetwothree.ui.searchCity.view.SearchCityFragment
import com.grappim.weatherninetwothree.utils.constants.INPUT_DEBOUNCE
import com.grappim.weatherninetwothree.utils.delegate.boolean
import com.grappim.weatherninetwothree.utils.delegate.double
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val searchLocationUseCase: SearchLocationUseCase,
    private val getCurrentPlaceUseCase: GetCurrentPlaceUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _searchLocationResult = MutableSharedFlow<SearchLocationResult>()
    val searchLocationResult: SharedFlow<SearchLocationResult>
        get() = _searchLocationResult.asSharedFlow()

    private val _searchInput = MutableSharedFlow<String>()
    val searchInput: SharedFlow<String>
        get() = _searchInput.asSharedFlow()

    /**
     * This field is needed to return the button state onBackPressed
     */
    var isButtonOkVisible: Boolean by savedStateHandle.boolean(SearchCityFragment.ARG_KEY_BTN_OK_VISIBILITY)

    var latitude: Double? by savedStateHandle.double(SearchCityFragment.ARG_KEY_LATITUDE)
    var longitude: Double? by savedStateHandle.double(SearchCityFragment.ARG_KEY_LONGITUDE)

    var isGetLocationClicked: Boolean = false
    var isLocationSelectedFromList: Boolean = false

    private val _foundLocation = MutableSharedFlow<GetCurrentLocationResult>()
    val foundLocation: SharedFlow<GetCurrentLocationResult>
        get() = _foundLocation.asSharedFlow()

    init {
        viewModelScope.launch {
            _searchInput
                .filter { it.length >= MIN_EDIT_TEXT_INPUT_SIZE }
                .debounce(INPUT_DEBOUNCE)
                .onEach { _searchLocationResult.emit(SearchLocationResult.Loading) }
                .mapLatest(::handleSearchLocationResult)
                .collect {
                    _searchLocationResult.emit(it)
                }
        }
    }

    private suspend fun handleSearchLocationResult(query: String): SearchLocationResult {
        return when (val searchLocationResult =
            searchLocationUseCase.invoke(SearchLocationParams(query))
        ) {
            is Try.Success -> {
                SearchLocationResult.SuccessResult(searchLocationResult.result)
            }
            is Try.Error -> {
                SearchLocationResult.ErrorResult(searchLocationResult.result)
            }
        }
    }

    fun getCurrentPlace() {
        viewModelScope.launch {
            _foundLocation.emit(GetCurrentLocationResult.Loading)
            val result = getCurrentPlaceUseCase.invoke(
                GetCurrentPlaceParams(
                    longitude = requireNotNull(longitude),
                    latitude = requireNotNull(latitude)
                )
            )

            when (result) {
                is Try.Success -> {
                    _foundLocation.emit(GetCurrentLocationResult.SuccessResult(result.result))
                }
                is Try.Error -> {
                    _foundLocation.emit(GetCurrentLocationResult.ErrorResult(result.result))
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