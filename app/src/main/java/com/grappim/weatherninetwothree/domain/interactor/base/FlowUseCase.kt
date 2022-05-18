package com.grappim.weatherninetwothree.domain.interactor.base

import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

/**
 * Executes business logic in its execute method and keep posting updates to the result as
 * [Result<R>].
 * Handling an exception (emit [Try.Error] to the result) is the subclasses's responsibility.
 */
abstract class FlowUseCase<in Params, R>(
    private val coroutineDispatcher: CoroutineDispatcher
) {
    operator fun invoke(params: Params): Flow<Try<R>> = execute(params)
        .onStart {
            emit(Try.Loading)
        }
        .catch { e ->
            Timber.e(e)
            emit(Try.Error(e))
        }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(params: Params): Flow<Try<R>>
}
