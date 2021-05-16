package com.fabyloso.guide_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fabyloso.core.data.remote.ApiSuccessResponse
import com.fabyloso.guide_list.data.remote.GuideRemoteController
import com.fabyloso.guide_list.data.remote.GuideService
import com.fabyloso.guide_list.data.remote.model.DataDto
import com.fabyloso.guide_list.data.remote.model.VenueDto
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GuideServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: GuideService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        service = GuideRemoteController().liveDataRetrofit
            .newBuilder()
            .baseUrl(mockWebServer.url("/"))
            .build().create(GuideService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getEvents() {
        enqueueResponse(dataJson)
        val dataDto =
            (service.getUpcomingEvents().getOrAwaitValue() as ApiSuccessResponse<DataDto>).body.data
        val request = mockWebServer.takeRequest()

        assertEquals(15, dataDto.size)
        assertEquals("/service/v2/upcomingGuides/", request.path)

        val firstEvent = dataDto.first()
        assertEquals("/guide/172296", firstEvent.url)
        assertEquals("May 18, 2021", firstEvent.startDate)
        assertEquals("May 19, 2021", firstEvent.endDate)
        assertEquals("CMSF 2021", firstEvent.name)
        assertEquals(
            "https://s3.amazonaws.com/media.guidebook.com/service/gzw2HXPX2Rs7GVk0j2ZAGuqzo20d1NQ8MPBQ3kpu/logo.png",
            firstEvent.icon
        )
        assertEquals(VenueDto(), firstEvent.venue)
        assertEquals("guide", firstEvent.objType)
        assertEquals(false, firstEvent.loginRequired)
    }

    private fun enqueueResponse(jsonBody: String) {
        val mockResponse = MockResponse()
        mockWebServer.enqueue(mockResponse.setBody(jsonBody))
    }

    private val dataJson = """
        {
            "data": [
                {
                    "url": "/guide/172296",
                    "startDate": "May 18, 2021",
                    "endDate": "May 19, 2021",
                    "name": "CMSF 2021",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/gzw2HXPX2Rs7GVk0j2ZAGuqzo20d1NQ8MPBQ3kpu/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                },
                {
                    "url": "/guide/183343",
                    "startDate": "May 19, 2021",
                    "endDate": "May 21, 2021",
                    "name": "PMMCON 2021 - Property Management Mastermind Conference",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/6MllEIefhfTW5PRVvcdbHk3n0aQLiMtDhG3N89ao/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                },
                {
                    "url": "/guide/183015",
                    "startDate": "May 19, 2021",
                    "endDate": "May 21, 2021",
                    "name": "Colorado ASBO 68th Annual Conference",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/8A3gMQe9n6ZtfPZJOk091BeGiAjKll444eYncLrU/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                },
                {
                    "url": "/guide/167040",
                    "startDate": "May 20, 2021",
                    "endDate": "May 21, 2021",
                    "name": "2021 Ohio Stormwater Conference",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/oBnTEhHqxrsjdHCJ8gPe7kVkHS62pxPl0rWyeYJu/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                },
                {
                    "url": "/guide/179717",
                    "startDate": "May 21, 2021",
                    "endDate": "May 23, 2021",
                    "name": "Pensacon 2021",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/5ElODVZdqWDgG7CFie77p6TaF0yvg4nYOIguXlBq/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                },
                {
                    "url": "/guide/181154",
                    "startDate": "May 23, 2021",
                    "endDate": "May 28, 2021",
                    "name": "Boston College Commencement 2021",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/hzetOyG6shoCPsvhIIOID8lnw4JfLB6s2EEcuXnd/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                },
                {
                    "url": "/guide/167337",
                    "startDate": "May 24, 2021",
                    "endDate": "Jun 19, 2021",
                    "name": "National History Day 2021",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/yCdnjneQGD8QzSwO8AMgkTRVk127n4LznfybD3S9/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                },
                {
                    "url": "/guide/168577",
                    "startDate": "May 26, 2021",
                    "endDate": "May 26, 2021",
                    "name": "Harbor 2021",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/uC5wYcjIsMQmXALAkMhe2KHv5bQPdhwP4Cx6sOIu/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                },
                {
                    "url": "/guide/173182",
                    "startDate": "May 30, 2021",
                    "endDate": "Jun 02, 2021",
                    "name": "2021 Texas Annual Conference of the Methodist Church",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/9UwwVjs84oH6GLMLu3fSarUSEok5GfMw6we6jo31/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                },
                {
                    "url": "/guide/174509",
                    "startDate": "Jun 10, 2021",
                    "endDate": "Jun 11, 2021",
                    "name": "ASAIO 66th Annual Conference",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/LtYC45kR6TrNQinWT1sZXPkcCUXkRe5dLB8ku9TX/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                },
                {
                    "url": "/guide/174251",
                    "startDate": "Jun 12, 2021",
                    "endDate": "Jun 12, 2021",
                    "name": "2021 Virginia Synod Assembly",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/bILU4NatOlV3PF2BPjIn53EcX2nm4dmVRTz9tkYm/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                },
                {
                    "url": "/guide/171058",
                    "startDate": "Jul 08, 2021",
                    "endDate": "Jul 11, 2021",
                    "name": "Anime Matsuri",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/FyCNzI2sk8wGkKFMajHkcgRwXITdQW2b7MYp0s9F/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                },
                {
                    "url": "/guide/179565",
                    "startDate": "Jul 22, 2021",
                    "endDate": "Jul 24, 2021",
                    "name": "Price Wedding 2021",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/IwgKjEhkg1N0jcAVaMO8euZj6ohohjclnjGJV1j2/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                },
                {
                    "url": "/guide/180432",
                    "startDate": "Oct 22, 2021",
                    "endDate": "Oct 24, 2021",
                    "name": "2021 MAG House of Delegates",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/RKw09mFIs2O2vicnzBhXl20zApDUm7GnZT5eNHFo/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                },
                {
                    "url": "/guide/148263",
                    "startDate": "Nov 01, 2021",
                    "endDate": "Nov 01, 2021",
                    "name": "Balfour Beatty End of Year Event 2021",
                    "icon": "https://s3.amazonaws.com/media.guidebook.com/service/sz98uQa499KttfRhe8fvHfHg6Zu6QRBHqn7HdC8a/logo.png",
                    "venue": {},
                    "objType": "guide",
                    "loginRequired": false
                }
            ],
            "total": "16"
        }
    """.trimIndent()
}