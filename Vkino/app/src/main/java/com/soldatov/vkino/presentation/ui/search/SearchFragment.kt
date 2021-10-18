package com.soldatov.vkino.presentation.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.result.FilmsSliderResult
import com.soldatov.domain.models.result.FilmsResult
import com.soldatov.data.repository.FILM_SEARCH_MODE
import com.soldatov.domain.models.FilmInfo
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.FragmentSearchBinding
import com.soldatov.vkino.presentation.ui.film.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment(), SearchFilmsAdapter.OnFilmClickListener {

    private lateinit var binding: FragmentSearchBinding
    private val searchFilmsAdapter = SearchFilmsAdapter()
    private val viewModel by sharedViewModel<SearchFragmentViewModel>()
    private var loading = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()

        binding.searchFilmsList.adapter = searchFilmsAdapter
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.searchFilmsList.layoutManager = layoutManager

        binding.searchValue.setQuery(viewModel.getSearchQuery(), false)

        binding.searchValue.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                binding.searchValue.clearFocus()
                val searchQuery = binding.searchValue.query.toString()
                viewModel.setSearchQuery(searchQuery)
                searchFilmsAdapter.setSearchFilms(emptyList(), this@SearchFragment)
                binding.preloadLayout.visibility = View.VISIBLE
                return false
            }
        })

        binding.searchFilmsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (loading && ((visibleItemCount + pastVisibleItems) >= totalItemCount)) {
                        loading = false
                        viewModel.nextPage()
                    }
                }
            }
        })
    }


    @SuppressLint("SetTextI18n")
    private fun setupObservers() {
        viewModel.searchFilms.observe(viewLifecycleOwner, {
            when (it) {
                is FilmsResult.Success -> {
                    showSearchFilms(it.data.films)
                    if (!it.data.hasMore){
                        searchFilmsAdapter.removeProgress()
                    } else {
                        searchFilmsAdapter.addProgress()
                        loading = true
                    }
                    binding.searchResultText.text =
                        "По Вашему запросу найдено: ${it.data.totalResults}"
                    binding.preloadLayout.visibility = View.INVISIBLE
                }
                is FilmsSliderResult.Error -> {

                }
                FilmsSliderResult.Loading -> {

                }
            }
        })
    }

    private fun showSearchFilms(filmInfo: List<FilmInfo>) {
        searchFilmsAdapter.setSearchFilms(filmInfo, this)
    }

    override fun onSearchFilmClick(filmId: Long) {
        findNavController().navigate(
            R.id.action_searchFragment_to_filmFragment,
            bundleOf(FILM_ID_KEY to filmId, FILM_MODE_KEY to FILM_SEARCH_MODE)
        )
    }
}