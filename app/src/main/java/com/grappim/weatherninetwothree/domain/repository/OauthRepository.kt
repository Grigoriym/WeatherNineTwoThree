package com.grappim.weatherninetwothree.domain.repository

import com.grappim.weatherninetwothree.domain.model.auth.Token

interface OauthRepository {

    suspend fun getToken(): Token

}