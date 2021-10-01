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
import com.soldatov.domain.models.FilmSliderInfo
import com.soldatov.vkino.databinding.FragmentHomeBinding
import com.soldatov.vkino.presentation.viewmodel.HomeFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val topSliderAdapter = TopSliderAdapter()
    private val homePageFilmsAdapter = HomePageFilmsAdapter()
    private val viewModel by sharedViewModel<HomeFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        binding.topSlider.adapter = topSliderAdapter
        binding.topSlider.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        binding.homePageFilms.adapter = homePageFilmsAdapter
        binding.homePageFilms.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun setupObservers() {
        viewModel.topSliderInfo.observe(viewLifecycleOwner, {
            when (it) {
                is FilmsSliderResult.Success -> {
                    showTopSlider(it.data)
                }
                is FilmsSliderResult.Error -> {

                }
                FilmsSliderResult.Loading -> {

                }
            }
        })
        viewModel.homePageFilms.observe(viewLifecycleOwner, {
            when (it) {
                is FilmsSliderResult.Success -> {
                    showHomePageFilms(it.data)
                }
                is FilmsSliderResult.Error -> {

                }
                FilmsSliderResult.Loading -> {

                }
            }
        })
    }

    private fun showTopSlider(filmSliderInfo: List<FilmSliderInfo>) {
        topSliderAdapter.setTopSliderInfo(filmSliderInfo, findNavController())
    }

    private fun showHomePageFilms(filmSliderInfo: List<FilmSliderInfo>) {
        homePageFilmsAdapter.setHomePageFilms(filmSliderInfo, findNavController())
    }
}