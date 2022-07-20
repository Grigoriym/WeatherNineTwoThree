package com.grappim.weatherninetwothree.ui.searchCity

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.grappim.test_shared.CoroutineRule
import com.grappim.test_shared.testException
import com.grappim.weatherninetwothree.domain.interactor.getCurrentPlace.GetCurrentPlaceUseCase
import com.grappim.weatherninetwothree.domain.interactor.searchLocation.SearchLocationUseCase
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.location.CurrentLocation
import com.grappim.weatherninetwothree.domain.model.location.FoundLocation
import com.grappim.weatherninetwothree.ui.searchCity.model.GetCurrentLocationResult
import com.grappim.weatherninetwothree.ui.searchCity.model.SearchLocationResult
import com.grappim.weatherninetwothree.ui.searchCity.view.SearchCityFragment
import com.grappim.weatherninetwothree.ui.searchCity.viewModel.SearchCityViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchCityViewModelTest {

    companion object {
        private const val SEARCH_QUERY = "search_query"
        private const val EMPTY_SEARCH_QUERY = ""
    }

    @get:Rule
    val coroutineRule = CoroutineRule()

    @MockK
    lateinit var searchLocationUseCaseMock: SearchLocationUseCase

    @MockK
    lateinit var getCurrentPlaceUseCaseMock: GetCurrentPlaceUseCase

    private lateinit var viewModel: SearchCityViewModel

    private val searchLocationResult = listOf(
        FoundLocation(cityName = "Almaty", longitude = 1.0, latitude = 2.0)
    )

    private val currentLocation = CurrentLocation(
        name = "Almaty",
        country = "KZ"
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        viewModel = SearchCityViewModel(
            searchLocationUseCaseMock,
            getCurrentPlaceUseCaseMock,
            SavedStateHandle(
                mapOf(
                    SearchCityFragment.ARG_KEY_LATITUDE to 1.0,
                    SearchCityFragment.ARG_KEY_LONGITUDE to 2.0
                )
            )
        )
    }

    @Test
    fun `calling searchLocation on success returns SuccessResult`() = runTest {
        runCurrent()
        coEvery {
            searchLocationUseCaseMock.invoke(any())
        } returns Try.Success(searchLocationResult)

        launch {
            viewModel.searchLocationResult.test {
                awaitItem() shouldBeInstanceOf SearchLocationResult.Loading::class.java

                with(awaitItem()) {
                    this shouldBeInstanceOf SearchLocationResult.SuccessResult::class.java
                    val result = (this as SearchLocationResult.SuccessResult).result
                    result shouldBe searchLocationResult
                }

                coVerify(exactly = 1) {
                    searchLocationUseCaseMock.invoke(any())
                }

                cancelAndIgnoreRemainingEvents()
            }
        }

        viewModel.searchLocation(SEARCH_QUERY)
        runCurrent()
    }

    @Test
    fun `calling searchLocation on error returns ErrorResult`() = runTest {
        runCurrent()
        coEvery {
            searchLocationUseCaseMock.invoke(any())
        } returns Try.Error(testException)

        launch {
            viewModel.searchLocationResult.test {
                awaitItem() shouldBeInstanceOf SearchLocationResult.Loading::class.java

                with(awaitItem()) {
                    this shouldBeInstanceOf SearchLocationResult.ErrorResult::class.java
                    val result = (this as SearchLocationResult.ErrorResult).result
                    result shouldBeInstanceOf IllegalStateException::class.java
                }

                coVerify(exactly = 1) {
                    searchLocationUseCaseMock.invoke(any())
                }

                cancelAndIgnoreRemainingEvents()
            }
        }

        viewModel.searchLocation(SEARCH_QUERY)
        runCurrent()
    }

    @Test
    fun `calling searchLocation with empty searchQuery does nothing`() = runTest {
        runCurrent()
        launch {
            viewModel.searchLocationResult.test {
                coVerify(exactly = 0) {
                    searchLocationUseCaseMock.invoke(any())
                }

                cancelAndIgnoreRemainingEvents()
            }
        }

        viewModel.searchLocation(EMPTY_SEARCH_QUERY)
        runCurrent()
    }

    @Test
    fun `calling getCurrentPlace with success returns SuccessResult`() = runTest {
        coEvery {
            getCurrentPlaceUseCaseMock.invoke(any())
        } returns Try.Success(currentLocation)

        runCurrent()

        launch {
            viewModel.foundLocation.test {
                awaitItem() shouldBeInstanceOf GetCurrentLocationResult.Loading::class.java

                with(awaitItem()) {
                    this shouldBeInstanceOf GetCurrentLocationResult.SuccessResult::class.java
                    val result = (this as GetCurrentLocationResult.SuccessResult).result
                    result shouldBe currentLocation
                }

                cancelAndIgnoreRemainingEvents()
            }
        }

        viewModel.getCurrentPlace()
        runCurrent()
    }

    @Test
    fun `calling getCurrentPlace with error returns ErrorResult`() = runTest {
        coEvery {
            getCurrentPlaceUseCaseMock.invoke(any())
        } returns Try.Error(testException)

        runCurrent()

        launch {
            viewModel.foundLocation.test {
                awaitItem() shouldBeInstanceOf GetCurrentLocationResult.Loading::class
                awaitItem() shouldBeInstanceOf GetCurrentLocationResult.ErrorResult::class

                cancelAndIgnoreRemainingEvents()
            }
        }

        viewModel.getCurrentPlace()
        runCurrent()
    }

}