package com.grappim.weatherninetwothree.ui.weatherDetails.viewModel

import app.cash.turbine.test
import com.grappim.weatherninetwothree.domain.interactor.options.OptionsUseCase
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.interactor.weatherData.GetWeatherDataUseCase
import com.grappim.weatherninetwothree.domain.model.weather.CurrentWeather
import com.grappim.weatherninetwothree.domain.model.weather.DailyWeather
import com.grappim.weatherninetwothree.domain.model.weather.WeatherDetails
import com.grappim.weatherninetwothree.ui.searchCity.model.CurrentLocationInfo
import com.grappim.test_shared.CoroutineRule
import com.grappim.test_shared.testException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherDetailsViewModelTest {

    companion object {
        private const val LATITUDE = 23.33
        private const val LONGITUDE = 24.55
        private const val NAME = "name"
        private const val TIMEZONE = "timezone"
    }

    private val currentWeather = CurrentWeather(
        icon = "",
        description = "",
        main = "",
        temp = "",
        feelsLike = 0.0,
        humidity = 0,
        windSpeed = 0.0,
        weatherCondition = "",
        currentTime = ""
    )

    private val daily = listOf(
        DailyWeather(
            morningTemp = 0.0,
            dayTemperature = "",
            eveningTemperature = 0.0,
            nightTemperature = "",
            humidity = 0,
            windSpeed = 0.0,
            weatherCondition = "",
            icon = "",
            description = "",
            main = "",
            time = ""
        )
    )

    private val weatherDetails = WeatherDetails(
        timezone = TIMEZONE,
        currentWeather = currentWeather,
        daily = daily
    )

    @get:Rule
    val coroutineRule = com.grappim.test_shared.CoroutineRule()

    @MockK
    lateinit var getWeatherDataUseCaseMock: GetWeatherDataUseCase

    @MockK
    lateinit var optionsUseCase: OptionsUseCase

    private lateinit var viewModel: WeatherDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        viewModel = WeatherDetailsViewModel(
            getWeatherDataUseCase = getWeatherDataUseCaseMock,
            currentLocationInfo = CurrentLocationInfo(NAME, LONGITUDE, LATITUDE),
            optionsUseCase = optionsUseCase
        )
    }

    @Test
    fun `instantiating viewModel calls getWeatherDataUseCase with success results in Success`() =
        runTest {
            success()
            launch {
                viewModel.weatherDetails.test {
                    with(awaitItem()) {
                        this shouldBeInstanceOf WeatherDataResult.SuccessResult::class.java
                        (this as WeatherDataResult.SuccessResult).result shouldBe weatherDetails
                    }
                    cancelAndIgnoreRemainingEvents()
                }
            }

            runCurrent()

            coVerify(exactly = 1) {
                getWeatherDataUseCaseMock.invoke(any())
            }
        }

    @Test
    fun `instantiating viewModel calls getWeatherDataUseCase with error results in Error`() =
        runTest {
            error()

            launch {
                viewModel.weatherDetails.test {
                    awaitItem() shouldBeInstanceOf WeatherDataResult.ErrorResult::class.java

                    cancelAndIgnoreRemainingEvents()
                }
            }

            runCurrent()

            coVerify(exactly = 1) {
                getWeatherDataUseCaseMock.invoke(any())
            }
        }

    private fun error() = runTest {
        coEvery {
            getWeatherDataUseCaseMock.invoke(any())
        } returns Try.Error(testException)
    }

    private fun success() = runTest {
        coEvery {
            getWeatherDataUseCaseMock.invoke(any())
        } returns Try.Success(weatherDetails)
    }

}