package com.soldatov.vkino.presentation.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.data.repository.FAV_SERIALS_MODE
import com.soldatov.domain.models.FilmInfo
import com.soldatov.domain.models.favourite.FavouriteResult
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.FragmentFavListBinding
import com.soldatov.vkino.presentation.ui.film.FILM_ID_KEY
import com.soldatov.vkino.presentation.ui.film.FILM_MODE_KEY
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ViewFavSerialsFragment: Fragment(), FavouriteAdapter.OnFilmClickListener {

    private lateinit var binding: FragmentFavListBinding
    private val favouriteAdapter = FavouriteAdapter()
    private val viewModel by sharedViewModel<FavouriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favList.adapter = favouriteAdapter
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.favList.layoutManager = layoutManager

        binding.favList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (viewModel.getFavSerialsLoading() && ((visibleItemCount + pastVisibleItems) >= totalItemCount)) {
                        viewModel.setFavSerialsLoading(false)
                        viewModel.serialsNextPage()
                    }
                }
            }
        })

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.favSerials.observe(viewLifecycleOwner, {
            when (it) {
                is FavouriteResult.Success -> {
                    showList(it.data)
                    if (!it.hasMore) {
                        favouriteAdapter.removeProgress()
                    } else {
                        favouriteAdapter.addProgress()
                        viewModel.setFavSerialsLoading(true)
                    }
                    binding.preloadLayout.visibility = View.INVISIBLE
                }
                is FavouriteResult.Error -> {

                }
            }
        })
    }

    private fun showList(films: List<FilmInfo>){
        favouriteAdapter.setFilms(films, this)
    }

    override fun onFilmClick(filmId: Long) {
        findNavController().navigate(
            R.id.action_favoriteFragment_to_filmFragment,
            bundleOf(FILM_ID_KEY to filmId, FILM_MODE_KEY to FAV_SERIALS_MODE)
        )
    }
}