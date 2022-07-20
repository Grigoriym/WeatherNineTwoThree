package com.grappim.weatherninetwothree.data.dataSource.remote

import com.grappim.test_shared.jsonForTesting
import com.grappim.test_shared.malformedJson
import com.grappim.weatherninetwothree.data.model.geocoding.AutocompleteResponseDTO
import com.grappim.weatherninetwothree.data.model.geocoding.FeatureDTO
import com.grappim.weatherninetwothree.data.model.geocoding.PropertiesDTO
import com.grappim.weatherninetwothree.data.network.service.GeoapifyService
import com.grappim.weatherninetwothree.domain.dataSource.remote.GeocodingAndSearchRemoteDataSource
import com.grappim.weatherninetwothree.domain.interactor.searchLocation.SearchLocationParams
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.mockk.MockKAnnotations
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
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

class GeocodingAndSearchRemoteDataSourceImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var geoApifyService: GeoapifyService
    private lateinit var geocodingAndSearchRemoteDataSource: GeocodingAndSearchRemoteDataSource

    private val client = OkHttpClient.Builder().build()

    private val searchLocationBody = """
        {
            "features":[
                {
                    "properties":{
                        "city":"Pattaya City",
                        "lon":100.8864587,
                        "lat":12.9366674
                    }
                }
            ]
        }
    """.trimIndent()
    private val searchLocationResponse = AutocompleteResponseDTO(
        features = listOf(
            FeatureDTO(
                properties = PropertiesDTO(
                    city = "Pattaya City",
                    lon = 100.8864587,
                    lat = 12.9366674
                )
            )
        )
    )

    @Before
    fun createServer() {
        MockKAnnotations.init(this)
        val contentType = "application/json".toMediaType()
        mockWebServer = MockWebServer()
        geoApifyService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(jsonForTesting.asConverterFactory(contentType))
            .build()
            .create()
        geocodingAndSearchRemoteDataSource = GeocodingAndSearchRemoteDataSourceImpl(
            geoapifyService = geoApifyService
        )
    }

    @After
    fun shutdownServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun `calling searchLocation with correct response is parsed into Success`() = runTest {
        val response = MockResponse()
            .setBody(searchLocationBody)
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val actual = geocodingAndSearchRemoteDataSource.searchLocation(
            SearchLocationParams("query")
        )

        actual shouldBeInstanceOf Try.Success::class
        val actualResult = (actual as Try.Success).result
        actualResult shouldBeEqualTo searchLocationResponse
    }

    @Test
    fun `calling searchLocation with malformed response is parsed into Error`() = runTest {
        val response = MockResponse()
            .setBody(malformedJson)
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val actual = geocodingAndSearchRemoteDataSource.searchLocation(
            SearchLocationParams("query")
        )

        actual shouldBeInstanceOf Try.Error::class
        val actualResult = (actual as Try.Error).result
        actualResult shouldBeInstanceOf SerializationException::class
    }

    @Test
    fun `calling searchLocation with error response code is parsed into HttpException Error result`() = runTest {
        val response = MockResponse()
            .setResponseCode(404)

        mockWebServer.enqueue(response)

        val actual = geocodingAndSearchRemoteDataSource.searchLocation(
            SearchLocationParams("query")
        )

        actual shouldBeInstanceOf Try.Error::class
        val actualResult = (actual as Try.Error).result
        actualResult shouldBeInstanceOf HttpException::class
    }
}