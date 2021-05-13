package com.fabyloso.core.data.remote

import android.util.Log
import com.fabyloso.core.data.common.Result
import retrofit2.Response

abstract class RemoteSource {

    protected suspend fun <T> getResult(
        expectedBody: Boolean = true,
        call: suspend () -> Response<T>
    ): Result<T?> {
        return try {
            val response = call()
            return when {
                response.isSuccessful && expectedBody.not() -> Result.optionalSuccess(response.body())
                response.isSuccessful && response.body() != null -> Result.success(response.body())
                else -> Result.error("${response.code()} ${response.message()}")
            }

        } catch (e: Exception) {
            error(e.message ?: e.toString())
        }
    }

    protected suspend fun <T> getResult(
        call: suspend () -> Response<T>
    ): Result<T> {
        return try {
            val response = call()
            return when {
                response.isSuccessful && response.body() != null -> Result.success(response.body()!!)
                else -> Result.error("${response.code()} ${response.message()}")
            }

        } catch (e: Exception) {
            error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        Log.d(this.javaClass.simpleName, message)
        return Result.error("Network call has failed for a following reason: $message")
    }

}