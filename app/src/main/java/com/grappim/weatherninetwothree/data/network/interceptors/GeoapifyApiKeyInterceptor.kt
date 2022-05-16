package com.grappim.weatherninetwothree.data.network.interceptors

import com.grappim.weatherninetwothree.di.AppBuildConfigProvider
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeoapifyApiKeyInterceptor @Inject constructor(
    private val appBuildConfigProvider: AppBuildConfigProvider
) : Interceptor {

    companion object {
        private const val API_KEY = "apiKey"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var requestBuilder = request.newBuilder()
        val annotation = getAnnotation(request)
        if (annotation != null) {
            val url = request.putAppIdQuery()
            requestBuilder = request.newBuilder().url(url)
        }

        return chain.proceed(requestBuilder.build())
    }

    private fun getAnnotation(request: Request): RequestWithGeoApifyApi? =
        request.tag(Invocation::class.java)
            ?.method()?.getAnnotation(RequestWithGeoApifyApi::class.java)

    private fun Request.putAppIdQuery(): HttpUrl =
        this.url.newBuilder().addQueryParameter(
            API_KEY,
            appBuildConfigProvider.geoApifyKey
        ).build()

}