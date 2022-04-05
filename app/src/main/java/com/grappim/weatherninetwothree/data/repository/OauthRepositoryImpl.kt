package com.grappim.weatherninetwothree.data.repository

import com.grappim.weatherninetwothree.data.model.oauth.OauthTokenBodyDTO
import com.grappim.weatherninetwothree.data.network.service.OauthService
import com.grappim.weatherninetwothree.di.QualifierOauthService
import com.grappim.weatherninetwothree.domain.model.auth.Token
import com.grappim.weatherninetwothree.domain.repository.OauthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OauthRepositoryImpl @Inject constructor(
    @QualifierOauthService private val oauthService: OauthService
) : OauthRepository {

    override suspend fun getToken(): Token {
        val response = oauthService.getOauth2Token(
            OauthTokenBodyDTO(
                grantType = "client_credentials"
            )
        )

        return Token(
            token = response.accessToken
        )
    }
}