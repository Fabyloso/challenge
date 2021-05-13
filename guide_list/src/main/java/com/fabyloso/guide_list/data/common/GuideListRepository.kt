package com.fabyloso.guide_list.data.common

import com.fabyloso.core.data.common.Repository
import com.fabyloso.guide_list.data.remote.GuideRemoteSource
import com.fabyloso.guide_list.data.remote.model.toEvent
import javax.inject.Inject

class GuideListRepository @Inject constructor(private val remoteSource: GuideRemoteSource) :
    Repository() {
    fun getUpcomingEvents() = resultFlow(
        networkCall = { remoteSource.getUpcomingEvents() },
        convertTo = { data -> data.data.map { it.toEvent() } }
    )
}