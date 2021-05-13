package com.fabyloso.core.data.common

import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class Repository {

    fun <T, A> resultFlow(
        networkCall: suspend () -> Result<A>,
        convertTo: (A) -> T,
        delayResponse: Boolean = false
    ) = flow {
        emit(Result.loading())
        val response = networkCall.invoke()
        val source = if (response.status == Result.Status.SUCCESS && response.data != null) {
            try {
                Result.success(convertTo(response.data))
            } catch (e: Exception) {
                Result.error(message = e.message ?: e.toString())
            }

        } else {
            Result.error(message = response.message)
        }
        //if response is too quick, delay the response to prevent blinking behavior
        if (delayResponse) delay(1000)
        emit(source)
    }.flowOn(Dispatchers.IO)
        .catch { Log.d(this.javaClass.simpleName, it.message, it.cause) }
        .asLiveData()
        .distinctUntilChanged()
}