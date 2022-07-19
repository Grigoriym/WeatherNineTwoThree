package com.grappim.weatherninetwothree.ui.searchCity

import androidx.lifecycle.SavedStateHandle
import com.grappim.weatherninetwothree.domain.interactor.getCurrentPlace.GetCurrentPlaceUseCase
import com.grappim.weatherninetwothree.domain.interactor.searchLocation.SearchLocationUseCase
import com.grappim.weatherninetwothree.ui.searchCity.viewModel.SearchCityViewModel
import com.grappim.weatherninetwothree.util.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchCityViewModelTest {

    companion object {
        private const val SEARCH_QUERY = "search_query"
    }

    @get:Rule
    val coroutineRule = CoroutineRule()

    @MockK
    lateinit var searchLocationUseCaseMock: SearchLocationUseCase

    @MockK
    lateinit var getCurrentPlaceUseCaseMock: GetCurrentPlaceUseCase

    private lateinit var viewModel: SearchCityViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        viewModel = SearchCityViewModel(
            searchLocationUseCaseMock,
            getCurrentPlaceUseCaseMock,
            SavedStateHandle()
        )
    }

    @Test
    fun `creating viewModel initial state`() {
        viewModel.isButtonOkVisible shouldBeEqualTo false
        viewModel.latitude shouldBeEqualTo null
        viewModel.longitude shouldBeEqualTo null
        viewModel.isGetLocationClicked shouldBeEqualTo false
        viewModel.isLocationSelectedFromList shouldBeEqualTo false
    }

}