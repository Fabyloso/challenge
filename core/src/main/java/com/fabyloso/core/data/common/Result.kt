package com.fabyloso.core.data.common

import android.util.Log

data class Result<out T>(val status: Status, val data: T?, val message: String = "") {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data)
        }

        fun <T> optionalSuccess(data: T?): Result<T?> {
            return Result(Status.SUCCESS, data)
        }

        fun <T> error(message: String, data: T? = null): Result<T> {
            Log.d("Result", message)
            return Result(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(Status.LOADING, data)
        }
    }
}