package com.fabyloso.core

import com.fabyloso.core.data.remote.ApiErrorResponse
import com.fabyloso.core.data.remote.ApiResponse
import com.fabyloso.core.data.remote.ApiSuccessResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun exception() {
        val exception = Exception("exception")
        assertEquals(ApiResponse.create<String>(exception).errorMessage, "exception")
    }

    @Test
    fun successWithBody() {
        val apiResponse =
            ApiResponse.create<String>(Response.success("success")) as ApiSuccessResponse
        assertEquals(apiResponse.body, "success")
    }

    @Test
    fun error() {
        val errorResponse = Response.error<String>(
            400,
            "error".toResponseBody("application/txt".toMediaTypeOrNull())
        )
        val error = ApiResponse.create<String>(errorResponse) as ApiErrorResponse<String>
        assertEquals(error.errorMessage, "error")
    }
}