package com.grappim.weatherninetwothree.data.dataSource.remote

import com.grappim.test_shared.jsonForTesting
import com.grappim.test_shared.malformedJson
import com.grappim.weatherninetwothree.data.model.geocoding.CurrentLocationDTO
import com.grappim.weatherninetwothree.data.model.weather.Current
import com.grappim.weatherninetwothree.data.model.weather.Weather
import com.grappim.weatherninetwothree.data.model.weather.WeatherCurrentDailyDTO
import com.grappim.weatherninetwothree.data.network.service.WeatherService
import com.grappim.weatherninetwothree.domain.dataSource.local.LocalOptionsDataSource
import com.grappim.weatherninetwothree.domain.dataSource.remote.WeatherRemoteDataSource
import com.grappim.weatherninetwothree.domain.interactor.getCurrentPlace.GetCurrentPlaceParams
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.interactor.weatherData.WeatherDataParams
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.create

class WeatherRemoteDataSourceImplTest {

    @MockK
    lateinit var optionsDataSource: LocalOptionsDataSource

    private lateinit var weatherRemoteDataSource: WeatherRemoteDataSource
    private lateinit var mockWebServer: MockWebServer
    private lateinit var weatherService: WeatherService

    private val client = OkHttpClient.Builder().build()

    private val currentLocationSuccess = listOf(CurrentLocationDTO(name = "Almaty", country = "KZ"))
    private val currentLocationSuccessBody = """
        [
            {
                "name":"Almaty",
                "country":"KZ"
            }
        ]
    """.trimIndent()

    private val weatherCurrentDailySuccess = WeatherCurrentDailyDTO(
        current = Current(
            dewPoint = 3.52,
            dt = 1658236496,
            feelsLike = 27.22,
            humidity = 20,
            pressure = 1013,
            sunrise = 1658186934,
            sunset = 1658240861,
            temp = 28.67,
            weather = listOf(
                Weather(
                    description = "broken clouds",
                    icon = "04d",
                    id = 803,
                    main = "Clouds"
                )
            ),
            windSpeed = 1.0
        ), daily = listOf(), lat = 43.1, lon = 76.1, timezone = "Asia/Almaty"
    )

    private val successBody = """
        {
            "lat":43.1,
            "lon":76.1,
            "timezone":"Asia/Almaty",
            "current":{
                "dew_point":3.52,
                "dt":1658236496,
                "feels_like":27.22,
                "humidity":20,
                "pressure":1013,
                "sunrise":1658186934,
                "sunset":1658240861,
                "temp":28.67,
                "wind_speed": 1.0
                "weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}]},
                "daily":[]
        }
    """.trimIndent()

    @Before
    fun createServer() {
        MockKAnnotations.init(this)
        val contentType = "application/json".toMediaType()
        mockWebServer = MockWebServer()
        weatherService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(jsonForTesting.asConverterFactory(contentType))
            .build()
            .create()
        weatherRemoteDataSource = WeatherRemoteDataSourceImpl(
            weatherService = weatherService,
            optionsDataSource = optionsDataSource
        )
    }

    @After
    fun shutdownServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun `calling getWeather with correct response is parsed into Success result`() = runTest {
        val expected = weatherCurrentDailySuccess

        val response = MockResponse()
            .setBody(successBody)
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val actual = weatherRemoteDataSource.getWeather(
            WeatherDataParams(
                latitude = 0.0,
                longitude = 0.0
            )
        )

        actual shouldBeInstanceOf Try.Success::class

        val actualResult = (actual as Try.Success).result
        actualResult shouldBeEqualTo expected
    }

    @Test
    fun `calling getWeather malformed response is parsed into json Error result`() = runTest {
        val response = MockResponse()
            .setBody(malformedJson)
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val actual = weatherRemoteDataSource.getWeather(
            WeatherDataParams(
                latitude = 0.0,
                longitude = 0.0
            )
        )

        actual shouldBeInstanceOf Try.Error::class

        val actualResult = (actual as Try.Error).result
        actualResult shouldBeInstanceOf SerializationException::class
    }

    @Test
    fun `calling getWeather with error response code is parsed into HttpException Error result`() =
        runTest {
            val response = MockResponse()
                .setResponseCode(404)

            mockWebServer.enqueue(response)

            val actual = weatherRemoteDataSource.getWeather(
                WeatherDataParams(
                    latitude = 0.0,
                    longitude = 0.0
                )
            )

            actual shouldBeInstanceOf Try.Error::class

            val actualResult = (actual as Try.Error).result
            actualResult shouldBeInstanceOf HttpException::class
        }

    @Test
    fun `calling getCurrentLocation correct response is parsed into Success result`() = runTest {
        val expected = currentLocationSuccess

        val response = MockResponse()
            .setBody(currentLocationSuccessBody)
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val actual = weatherRemoteDataSource.getCurrentLocation(
            GetCurrentPlaceParams(longitude = 0.0, latitude = 0.0)
        )

        actual shouldBeInstanceOf Try.Success::class

        val actualResult = (actual as Try.Success).result
        actualResult shouldBeEqualTo expected
    }

    @Test
    fun `calling getCurrentLocation malformed response is parsed into json Error result`() =
        runTest {
            val response = MockResponse()
                .setBody(malformedJson)
                .setResponseCode(200)

            mockWebServer.enqueue(response)

            val actual = weatherRemoteDataSource.getCurrentLocation(
                GetCurrentPlaceParams(longitude = 0.0, latitude = 0.0)
            )

            actual shouldBeInstanceOf Try.Error::class

            val actualResult = (actual as Try.Error).result
            actualResult shouldBeInstanceOf SerializationException::class
        }

    @Test
    fun `calling getCurrentLocation with error response code is parsed into HttpException Error result`() =
        runTest {
            val response = MockResponse()
                .setResponseCode(404)

            mockWebServer.enqueue(response)

            val actual = weatherRemoteDataSource.getCurrentLocation(
                GetCurrentPlaceParams(longitude = 0.0, latitude = 0.0)
            )

            actual shouldBeInstanceOf Try.Error::class

            val actualResult = (actual as Try.Error).result
            actualResult shouldBeInstanceOf HttpException::class
        }
}