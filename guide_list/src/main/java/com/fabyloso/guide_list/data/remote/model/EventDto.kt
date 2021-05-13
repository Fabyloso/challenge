package com.fabyloso.guide_list.data.remote.model

import com.fabyloso.guide_list.data.local.Event
import com.fabyloso.guide_list.data.local.Venue

data class EventDto(
    val url: String,
    val startDate: String,
    val endDate: String,
    val name: String,
    val icon: String,
    val objType: String,
    val loginRequired: Boolean,
    val venue: VenueDto
)

data class VenueDto(
    val city: String?,
    val state: String?
)

fun VenueDto.toVenue(): Venue {
    return Venue(city?:"", state?:"")
}

fun EventDto.toEvent(): Event {
    return Event(
        url,
        startDate,
        endDate,
        name,
        icon,
        objType,
        loginRequired,
        venue.toVenue() )
}