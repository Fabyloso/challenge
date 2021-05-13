package com.fabyloso.guide_list

import androidx.lifecycle.ViewModel
import com.fabyloso.guide_list.data.common.GuideListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuideListViewModel @Inject constructor(repository: GuideListRepository) : ViewModel() {

    val observeEvents by lazy { repository.getUpcomingEvents() }
}