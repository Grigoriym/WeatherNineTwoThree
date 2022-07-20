package com.grappim.test_shared

import kotlinx.serialization.json.Json

val jsonForTesting = Json {
    isLenient = true
    prettyPrint = false
    ignoreUnknownKeys = true
    explicitNulls = false
}