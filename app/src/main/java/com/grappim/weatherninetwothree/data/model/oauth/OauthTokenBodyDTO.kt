package com.grappim.weatherninetwothree.data.model.oauth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OauthTokenBodyDTO(
    @SerialName("grant_type")
    val grantType: String
)
