package com.grappim.weatherninetwothree.data.network.service

import com.grappim.weatherninetwothree.data.model.oauth.OauthTokenBodyDTO
import com.grappim.weatherninetwothree.data.model.oauth.OauthTokenResponseDTO
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OauthService {

    @POST("token")
    @FormUrlEncoded
    suspend fun getOauth2Token(
        @Body bodyDTO: OauthTokenBodyDTO
    ): OauthTokenResponseDTO

}