package com.grappim.weatherninetwothree.data.model.oauth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OauthTokenResponseDTO(
    @SerialName("access_token")
    val accessToken:String
)
