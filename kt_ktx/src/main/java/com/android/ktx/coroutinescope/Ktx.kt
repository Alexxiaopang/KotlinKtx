package com.android.ktx.coroutinescope

import android.telecom.Call
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException

object Ktx {
    fun <T> run(action: () -> T): IAwait<T> {
        return try {
            object : IAwait<T> {
                override suspend fun await(): T {
                    return action.invoke()
                }
            }
        } catch (t: Throwable) {
            throw t
        }
    }

}
