package com.fabyloso.guide_list.data.remote

import androidx.lifecycle.LiveData
import com.fabyloso.core.data.remote.ApiResponse
import com.fabyloso.guide_list.data.remote.model.DataDto
import retrofit2.http.GET

interface GuideService {
    @GET("service/v2/upcomingGuides/")
    fun getUpcomingEvents(): LiveData<ApiResponse<DataDto>>
}