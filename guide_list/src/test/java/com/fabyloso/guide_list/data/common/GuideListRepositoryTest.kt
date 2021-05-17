package com.fabyloso.guide_list.data.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.fabyloso.core.data.common.Resource
import com.fabyloso.core.data.remote.ApiResponse
import com.fabyloso.core.util.InstantAppExecutors
import com.fabyloso.guide_list.data.local.Event
import com.fabyloso.guide_list.data.remote.GuideService
import com.fabyloso.guide_list.data.remote.model.DataDto
import com.fabyloso.guide_list.data.remote.model.EventDto
import com.fabyloso.guide_list.data.remote.model.VenueDto
import com.fabyloso.guide_list.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import retrofit2.Response

@RunWith(JUnit4::class)
class GuideListRepositoryTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: GuideListRepository
    private val service = mock(GuideService::class.java)

    @Before
    fun setup() {
        repository = GuideListRepository(service, InstantAppExecutors())
    }

    @Test
    fun getGuides() {
        val stubbedData = DataDto(listOf(EventDto("", "", "", "", "", "", false, VenueDto())))
        val call = successCall(stubbedData)
        `when`(service.getUpcomingEvents()).thenReturn(call)
        val data = repository.getUpcomingEvents()
        val observer = mock<Observer<Resource<List<Event>>>>()
        data.observeForever(observer)

        verify(service).getUpcomingEvents()
        verifyNoMoreInteractions(service)
    }

    @Test
    fun getGuides_error() {
        val callError = MutableLiveData<ApiResponse<DataDto>>()
        `when`(service.getUpcomingEvents()).thenReturn(callError)
        val data = repository.getUpcomingEvents()
        val observer = mock<Observer<Resource<List<Event>>>>()
        data.observeForever(observer)

        verify(service).getUpcomingEvents()
        verify(observer).onChanged(Resource.loading(null))
        verifyNoMoreInteractions(service)

        callError.postValue(ApiResponse.create(Exception("error")))
        verify(observer).onChanged(Resource.error("error", null))
        verifyNoMoreInteractions(service)
    }

    private fun <T : Any> successCall(data: T) = createCall(Response.success(data))

    private fun <T : Any> createCall(response: Response<T>) =
        MutableLiveData<ApiResponse<T>>().apply {
            value = ApiResponse.create(response)
        } as LiveData<ApiResponse<T>>
}