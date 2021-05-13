package com.fabyloso.core.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class RemoteController {
    abstract val baseUrl: String
    abstract val interceptor: Interceptor

    private val baseClient
        get() = OkHttpClient().newBuilder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    open val retrofit: Retrofit
        get() {
            return Retrofit.Builder()
                .client(baseClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    val mockRetrofit: Retrofit
        get() {
            return retrofit.newBuilder().baseUrl("http://127.0.0.1:8080").build()
        }
}