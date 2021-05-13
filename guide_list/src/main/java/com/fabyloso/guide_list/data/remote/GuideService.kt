package com.fabyloso.guide_list.data.remote

import com.fabyloso.guide_list.data.remote.model.DataDto
import retrofit2.Response
import retrofit2.http.GET

interface GuideService {
    @GET("service/v2/upcomingGuides/")
    suspend fun getUpcomingEvents(): Response<DataDto>
}