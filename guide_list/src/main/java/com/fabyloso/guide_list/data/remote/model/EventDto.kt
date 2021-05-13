package com.fabyloso.guide_list.data.remote.model


import com.fabyloso.guide_list.data.local.Event

data class EventDto(
    val url: String,
    val startDate: String,
    val endDate: String,
    val name: String,
    val icon: String,
    val objType: String,
    val loginRequired: Boolean,
    val venue: List<Any>
)


fun EventDto.toEvent(): Event {
    return Event(url, startDate, endDate, name, icon, objType, loginRequired)
}