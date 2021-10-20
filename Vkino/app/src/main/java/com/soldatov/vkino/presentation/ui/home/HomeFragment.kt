package com.soldatov.vkino.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.result.FilmsResult
import com.soldatov.domain.models.result.FilmsSliderResult
import com.soldatov.data.repository.FILM_HOME_MODE
import com.soldatov.data.repository.FILM_SLIDER_MODE
import com.soldatov.domain.models.FilmInfo
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.FragmentHomeBinding
import com.soldatov.vkino.presentation.ui.film.FILM_ID_KEY
import com.soldatov.vkino.presentation.ui.film.FILM_MODE_KEY
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(), TopSliderAdapter.OnFilmClickListener,
    HomePageFilmsAdapter.OnFilmClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val topSliderAdapter = TopSliderAdapter()
    private val homePageFilmsAdapter = HomePageFilmsAdapter()
    private val viewModel by sharedViewModel<HomeFragmentViewModel>()
    private var loading = true

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
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.homePageFilms.layoutManager = layoutManager

        binding.nestedScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) {
                loading = false
                viewModel.nextPage()
            }
        })

        showOrderBy(viewModel.getCurrentOrder())
    }

    private fun showOrderBy(order: String) {
        val orderBy = viewModel.getOrderByData()
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.item_spinner, orderBy)
        val currentOrderBy = viewModel.getCurrentOrderBy()

        binding.orderBy.adapter = arrayAdapter
        binding.orderBy.setSelection(arrayAdapter.getPosition(currentOrderBy), true)
        binding.orderBy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setOrderBy(orderBy[position])
                showHomePageFilms(emptyList())
                binding.filmsProgress.visibility = View.VISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        if (order == "DESC") {
            binding.orderBtn.setImageResource(R.drawable.sort_desc)
            binding.orderBtn.tag = R.drawable.sort_desc
        } else {
            binding.orderBtn.setImageResource(R.drawable.sort_asc)
            binding.orderBtn.tag = R.drawable.sort_asc
        }

        binding.orderBtn.setOnClickListener {
            if (it.tag == R.drawable.sort_desc) {
                binding.orderBtn.setImageResource(R.drawable.sort_asc)
                binding.orderBtn.tag = R.drawable.sort_asc
            } else {
                binding.orderBtn.setImageResource(R.drawable.sort_desc)
                binding.orderBtn.tag = R.drawable.sort_desc
            }
            showHomePageFilms(emptyList())
            viewModel.switchOrder()
            binding.filmsProgress.visibility = View.VISIBLE
        }
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
                is FilmsResult.Success -> {
                    binding.filmsProgress.visibility = View.INVISIBLE
                    showHomePageFilms(it.data.films)
                    if (!it.data.hasMore) {
                        homePageFilmsAdapter.removeProgress()
                    } else {
                        homePageFilmsAdapter.addProgress()
                        loading = true
                    }
                }
                is FilmsResult.Error -> {

                }
                FilmsResult.Loading -> {
                    binding.filmsProgress.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showTopSlider(filmInfo: List<FilmInfo>) {
        topSliderAdapter.setTopSliderInfo(filmInfo, this)
    }

    private fun showHomePageFilms(filmInfo: List<FilmInfo>) {
        homePageFilmsAdapter.setHomePageFilms(filmInfo, this)
    }

    override fun onSliderFilmClick(filmId: Long) {
        findNavController().navigate(
            R.id.action_homeFragment_to_filmFragment,
            bundleOf(FILM_ID_KEY to filmId, FILM_MODE_KEY to FILM_SLIDER_MODE)
        )
    }

    override fun onHomeFilmClick(filmId: Long) {
        findNavController().navigate(
            R.id.action_homeFragment_to_filmFragment,
            bundleOf(FILM_ID_KEY to filmId, FILM_MODE_KEY to FILM_HOME_MODE)
        )
    }
}