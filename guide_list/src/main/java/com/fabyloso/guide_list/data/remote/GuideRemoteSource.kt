package com.fabyloso.guide_list.data.remote

import com.fabyloso.core.data.remote.RemoteSource
import javax.inject.Inject

class GuideRemoteSource @Inject constructor(private val service: GuideService) :
    RemoteSource() {
    suspend fun getUpcomingEvents() = getResult { service.getUpcomingEvents() }
}