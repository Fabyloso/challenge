package com.fabyloso.guide_list.ui.guide_list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fabyloso.core.data.common.Resource.Status.*
import com.fabyloso.core.databinding.LoadingLayoutBinding
import com.fabyloso.core.util.InAppMessageHelper
import com.fabyloso.guide_list.R
import com.fabyloso.guide_list.databinding.GuideListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GuideListFragment : Fragment(R.layout.guide_list_fragment) {
    private var _binding: GuideListFragmentBinding? = null
    private var _loadingBinding: LoadingLayoutBinding? = null

    private val binding get() = _binding!!
    private val loadingBinding get() = _loadingBinding!!

    private val viewModel: GuideListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = GuideListFragmentBinding.inflate(inflater, container, false)
        _loadingBinding = LoadingLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = GuideListAdapter {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://guidebook.com/${it.url}")))
        }
        viewModel.observeEvents.observe(viewLifecycleOwner) {
            when (it.status) {
                LOADING -> {
                    loadingBinding.loadingGroup.isGone = false
                }
                SUCCESS -> {
                    binding.guideRv.adapter = adapter.apply { items = it.data ?: emptyList() }
                    loadingBinding.loadingGroup.isGone = true
                }
                ERROR -> {
                    InAppMessageHelper.popSnackIndefinite(binding.root, it.message)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}