package com.testtapyou.core.utils

import kotlin.coroutines.cancellation.CancellationException

/**
 *  https://github.com/Kotlin/kotlinx.coroutines/issues/1814
 *
 *  [runCatching] который не ловит [CancellationException]
 *
 *  The suspend keyword is added to prevent this new function from being called in regular non-suspending/blocking code.
 */
inline suspend fun <R> runCatchingCancelable(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (c: CancellationException) {
        throw c
    } catch (e: Throwable) {
        Result.failure(e)
    }
}