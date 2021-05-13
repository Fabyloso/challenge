package com.fabyloso.guide_list.data.remote

import android.util.Log
import com.fabyloso.core.data.remote.RemoteController
import okhttp3.Interceptor

class GuideRemoteController : RemoteController() {
    override val baseUrl: String
        get() = "https://guidebook.com/"
    override val interceptor: Interceptor
        get() = Interceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            var tries = 0
            while (response.isSuccessful.not().and(tries < 3)) {
                Log.d(this.javaClass.simpleName, "Request was not successful: ${request.url()}")
                Log.d(this.javaClass.simpleName, "Response Code: ${response.code()}")
                tries++
                response = chain.proceed(request)
            }
            response
        }
}

