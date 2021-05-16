
package com.fabyloso.core.data.common

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.fabyloso.core.data.remote.ApiEmptyResponse
import com.fabyloso.core.data.remote.ApiErrorResponse
import com.fabyloso.core.data.remote.ApiResponse
import com.fabyloso.core.data.remote.ApiSuccessResponse

abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        fetchFromNetwork()
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        setValue(Resource.loading(null))

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.mainThread().execute {
                        setValue(Resource.success(processResponse(response)))
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        setValue(Resource.success(null))
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    setValue(Resource.error(response.errorMessage, null))
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
    protected abstract fun processResponse(response: ApiSuccessResponse<RequestType>): ResultType
}
