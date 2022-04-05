package com.grappim.weatherninetwothree.data.model.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseApiErrorDTO(
    val system: String?,
    val status: String?,
    @SerialName("status_code")
    val statusCode: String,
    val message: String,
    val developerMessage: String?
)