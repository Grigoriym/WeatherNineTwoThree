package com.grappim.weatherninetwothree.data.network.interceptors

import com.grappim.weatherninetwothree.data.model.base.BaseApiErrorDTO
import com.grappim.weatherninetwothree.data.network.utils.NetworkHandler
import com.grappim.weatherninetwothree.domain.model.base.BaseApiError
import com.grappim.weatherninetwothree.domain.model.exception.NetworkException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorMappingInterceptor @Inject constructor(
    private val networkHandler: NetworkHandler
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        try {
            if (!networkHandler.isConnected) throw NetworkException(NetworkException.ERROR_NO_INTERNET)
            val request = chain.request()
            val response = chain.proceed(request)
            if (response.isSuccessful) {
                response
            } else {
                throw mapErrorBodyToException(response)
            }

        } catch (throwable: Throwable) {
            throw throwable.mapNetworkException()
        }

    private fun mapErrorBodyToException(response: Response): Throwable {
        val responseAsString: String = response.body?.string() ?: ""
        val baseApiErrorAm = Json.decodeFromString<BaseApiErrorDTO>(responseAsString)
        val baseApiError = BaseApiError(
            system = baseApiErrorAm.system,
            status = baseApiErrorAm.status,
            statusCode = baseApiErrorAm.statusCode,
            message = baseApiErrorAm.message,
            developerMessage = baseApiErrorAm.developerMessage
        )
        return NetworkException(
            errorCode = NetworkException.ERROR_API,
            apiError = baseApiError,
            request = "[Request] ${response.request.method} ${response.request.url}"
        )
    }

    private fun Throwable.mapNetworkException(): Throwable =
        when (this) {
            is NetworkException -> this
            is SocketTimeoutException -> NetworkException(NetworkException.ERROR_TIMEOUT, this)
            is UnknownHostException -> NetworkException(NetworkException.ERROR_HOST_NOT_FOUND, this)
            is IOException -> NetworkException(NetworkException.ERROR_NETWORK_IO, this)
            else -> NetworkException(NetworkException.ERROR_UNDEFINED, this)
        }

}