package com.soldatov.vkino.presentation.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.data.api.request_status.FilmsSliderResult
import com.soldatov.data.api.request_status.SearchResult
import com.soldatov.data.repository.FILM_SEARCH_MODE
import com.soldatov.domain.models.FilmInfo
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.FragmentSearchBinding
import com.soldatov.vkino.presentation.ui.film.*
import com.soldatov.vkino.presentation.viewmodel.SearchFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment(), SearchFilmsAdapter.OnFilmClickListener {

    private lateinit var binding: FragmentSearchBinding
    private val searchFilmsAdapter = SearchFilmsAdapter()
    private val viewModel by sharedViewModel<SearchFragmentViewModel>()

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
        binding.searchFilmsList.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.searchValue.setQuery(viewModel.getSearchQuery(), false)

        binding.searchValue.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                binding.searchValue.clearFocus()
                viewModel.setSearchQuery(binding.searchValue.query.toString())
                binding.preloadLayout.visibility = View.VISIBLE
                return false
            }
        })
    }



    @SuppressLint("SetTextI18n")
    private fun setupObservers() {
        viewModel.searchFilms.observe(viewLifecycleOwner, {
            when (it) {
                is SearchResult.Success -> {
                    showSearchFilms(it.data.films)
                    binding.searchResultText.text = "По Вашему запросу найдено: ${it.data.totalResults}"
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