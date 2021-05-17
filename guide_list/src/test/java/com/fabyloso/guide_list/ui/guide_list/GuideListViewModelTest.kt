package com.fabyloso.guide_list.ui.guide_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.fabyloso.core.data.common.Resource
import com.fabyloso.guide_list.data.common.GuideListRepository
import com.fabyloso.guide_list.data.local.Event
import com.fabyloso.guide_list.mock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class GuideListViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mock<GuideListRepository>()
    private var viewModel = GuideListViewModel(repository)

    @Test
    fun observeEvents() {
        verify(repository, times(0)).getUpcomingEvents()
        val result = MutableLiveData(Resource(Resource.Status.SUCCESS, listOf<Event>()))
        `when`(repository.getUpcomingEvents()).thenReturn(result)
        val observer = mock<Observer<Resource<List<Event>>>>()
        viewModel.observeEvents.observeForever(observer)
        verify(repository).getUpcomingEvents()
        verify(observer).onChanged(result.value)
    }
}