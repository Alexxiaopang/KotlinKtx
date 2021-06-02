package com.android.ktx.coroutinescope

import android.telecom.Call
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.IOException

object Ktx {
    //默认是在Io线程运行
    fun <T> run(action: suspend () -> T): IAwait<T> {
        return try {
            object : IAwait<T> {
                override suspend fun await(): T {
                    return withContext(Dispatchers.IO) { action.invoke() }
                }
            }
        } catch (t: Throwable) {
            throw t
        }
    }

    fun <T> runMain(action: suspend () -> T): IAwait<T> {
        return try {
            object : IAwait<T> {
                override suspend fun await(): T {
                    return withContext(Dispatchers.Main) { action.invoke() }
                }
            }
        } catch (t: Throwable) {
            throw t
        }
    }

    fun <T> runIo(action: suspend () -> T): IAwait<T> {
        return try {
            object : IAwait<T> {
                override suspend fun await(): T {
                    return withContext(Dispatchers.IO) { action.invoke() }
                }
            }
        } catch (t: Throwable) {
            throw t
        }
    }

    fun <T> runDefault(action: suspend () -> T): IAwait<T> {
        return try {
            object : IAwait<T> {
                override suspend fun await(): T {
                    return withContext(Dispatchers.Default) { action.invoke() }
                }
            }
        } catch (t: Throwable) {
            throw t
        }
    }


}
