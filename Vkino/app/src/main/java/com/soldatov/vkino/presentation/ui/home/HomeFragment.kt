package com.soldatov.vkino.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.data.api.FilmsSliderResult
import com.soldatov.domain.models.FilmSliderInfo
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.FragmentHomeBinding
import com.soldatov.vkino.presentation.ui.film.FILM_HOME_KEY
import com.soldatov.vkino.presentation.ui.film.FILM_ID_KEY
import com.soldatov.vkino.presentation.ui.film.FILM_SLIDER_KEY
import com.soldatov.vkino.presentation.viewmodel.HomeFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(), TopSliderAdapter.OnFilmClickListener, HomePageFilmsAdapter.OnFilmClickListener {

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
        topSliderAdapter.setTopSliderInfo(filmSliderInfo, this)
    }

    private fun showHomePageFilms(filmSliderInfo: List<FilmSliderInfo>) {
        homePageFilmsAdapter.setHomePageFilms(filmSliderInfo, this)
    }

    override fun onSliderFilmClick(filmId: Long) {
        findNavController().navigate(
            R.id.action_homeFragment_to_filmFragment,
            bundleOf(FILM_ID_KEY to filmId, FILM_SLIDER_KEY to true))
    }

    override fun onHomeFilmClick(filmId: Long) {
        findNavController().navigate(
            R.id.action_homeFragment_to_filmFragment,
            bundleOf(FILM_ID_KEY to filmId, FILM_HOME_KEY to true))
    }
}