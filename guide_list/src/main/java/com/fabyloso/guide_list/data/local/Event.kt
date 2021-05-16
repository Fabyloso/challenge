package com.fabyloso.guide_list.data.local

data class Event(
    val url: String,
    val startDate: String,
    val endDate: String,
    val name: String,
    val icon: String,
    val objType: String,
    val loginRequired: Boolean,
    val venue: Venue?
)

class Venue(
    val city: String,
    val state: String
)

