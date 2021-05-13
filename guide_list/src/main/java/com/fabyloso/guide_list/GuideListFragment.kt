package com.fabyloso.guide_list

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuideListFragment : Fragment(R.layout.guide_list_fragment) {

    private val viewModel: GuideListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeEvents.observe(viewLifecycleOwner){
            view.findViewById<TextView>(R.id.text).text =it.data?.firstOrNull()?.name
        }

    }

}