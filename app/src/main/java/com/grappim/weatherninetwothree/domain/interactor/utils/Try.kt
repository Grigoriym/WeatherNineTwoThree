package com.grappim.weatherninetwothree.domain.interactor.utils

import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

sealed class Try<out S, out E> {
    data class Success<out S>(val result: S) : Try<S, Nothing>()
    data class Error<out E>(val result: E) : Try<Nothing, E>()
}

inline fun <S, R> S.runOperationCatching(block: S.() -> R): Try<R, Throwable> =
    try {
        Try.Success(block())
    } catch (e: CancellationException) {
        Timber.e(e)
        throw e
    } catch (e: Throwable) {
        Timber.e(e)
        Try.Error(e)
    }

inline fun <S, E> Try<S, E>.doOnError(block: (E) -> Unit): Try<S, E> {
    if (this is Try.Error) {
        block(this.result)
    }
    return this
}