package com.grappim.weatherninetwothree.domain.interactor.utils

sealed class Try<out Data> {
    object Loading : Try<Nothing>()
    object Empty : Try<Nothing>()
    object Initial : Try<Nothing>()
    data class Error(val exception: Throwable) : Try<Nothing>()
    data class Success<out Data>(val data: Data) : Try<Data>()
}