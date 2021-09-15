package com.soldatov.vkino.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.data.api.FilmsSliderResult
import com.soldatov.domain.models.DomainFilmSliderInfo
import com.soldatov.vkino.databinding.FragmentHomeBinding
import com.soldatov.vkino.presentation.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val topSliderAdapter = TopSliderAdapter()
    private val viewModel by sharedViewModel<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topSlider.adapter = topSliderAdapter
        binding.topSlider.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    private fun setupObservers() {
        viewModel.topSliderInfo.observe(viewLifecycleOwner, {
            when (it) {
                is FilmsSliderResult.Success -> {
                    setupUI(it.data)
                }
                is FilmsSliderResult.Error -> {

                }
                FilmsSliderResult.Loading -> {

                }
            }
        })
    }

    private fun setupUI(filmSliderInfo: List<DomainFilmSliderInfo>) {
        showTopSlider(filmSliderInfo)
    }

    private fun showTopSlider(filmSliderInfo: List<DomainFilmSliderInfo>) {
        topSliderAdapter.setTopSliderInfo(filmSliderInfo, findNavController())
    }
}