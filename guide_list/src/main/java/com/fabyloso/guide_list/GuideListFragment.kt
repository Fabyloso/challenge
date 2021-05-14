package com.fabyloso.guide_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fabyloso.guide_list.databinding.GuideListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuideListFragment : Fragment(R.layout.guide_list_fragment) {
    private var _binding: GuideListFragmentBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: GuideListViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = GuideListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = GuideListAdapter {

        }
        viewModel.observeEvents.observe(viewLifecycleOwner){
            binding.guideRv.adapter = adapter.apply { items = it.data?: emptyList() }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}