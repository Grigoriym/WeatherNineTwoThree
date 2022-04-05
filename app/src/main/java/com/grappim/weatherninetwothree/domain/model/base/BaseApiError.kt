package com.grappim.weatherninetwothree.domain.model.base

data class BaseApiError(
    val system: String?,
    val status: String?,
    val statusCode: String,
    val message: String,
    val developerMessage: String?
)