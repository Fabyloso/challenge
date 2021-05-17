package com.fabyloso.guide_list.data.common

import androidx.lifecycle.LiveData
import com.fabyloso.core.data.common.AppExecutors
import com.fabyloso.core.data.common.NetworkBoundResource
import com.fabyloso.core.data.remote.ApiResponse
import com.fabyloso.core.data.remote.ApiSuccessResponse
import com.fabyloso.guide_list.data.local.Event
import com.fabyloso.guide_list.data.remote.GuideService
import com.fabyloso.guide_list.data.remote.model.DataDto
import com.fabyloso.guide_list.data.remote.model.toEvent
import javax.inject.Inject

open class GuideListRepository @Inject constructor(
    private val remoteSource: GuideService,
    private val appExecutors: AppExecutors
) {

    open fun getUpcomingEvents() =
        object : NetworkBoundResource<List<Event>, DataDto>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<DataDto>> {
                return remoteSource.getUpcomingEvents()
            }

            override fun processResponse(response: ApiSuccessResponse<DataDto>): List<Event> {
                return response.body.data.map { it.toEvent() }
            }
        }.asLiveData()
}