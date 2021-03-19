package com.android.ktx.coroutinescope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async


suspend inline fun <T> CoroutineScope.newAsync(crossinline action: suspend () -> T): Deferred<T> {
    return async(SupervisorJob()) {
        action.invoke()
    }
}

//suspend fun <T> Deferred<T>.tryAwait(): T? {
//    return try {
//        await()
//    } catch (e: Throwable) {
//        null
//    }
//}

suspend inline fun <T> Deferred<T>.tryAwaitReturn(crossinline action: Throwable.() -> T): T {
    return try {
        await()
    } catch (e: Throwable) {
        action.invoke(e)
    }
}
