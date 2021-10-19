package com.soldatov.vkino.presentation.ui.filter.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.result.GenresResult
import com.soldatov.domain.models.GenresList
import com.soldatov.vkino.databinding.FragmentChooseGenreBinding
import com.soldatov.vkino.presentation.ui.filter.FilterFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChooseGenreFragment : Fragment() {

    private lateinit var binding: FragmentChooseGenreBinding
    private val genresListAdapter = GenresListAdapter()
    private val viewModel by sharedViewModel<FilterFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.genresList.adapter = genresListAdapter
        binding.genresList.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.submit.setOnClickListener {
            val chosenGenres = genresListAdapter.getChosenGenres()
            viewModel.setChosenGenres(chosenGenres)
            findNavController().popBackStack()
        }

        binding.searchGenre.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchGenre.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                genresListAdapter.filter.filter(newText)
                return true
            }
        })

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.genres.observe(viewLifecycleOwner, {
            when (it) {
                is GenresResult.Success -> {
                    showGenresList(it.data)
                }
            }
        })
    }

    private fun showGenresList(genres: GenresList) {
        val chosenGenres = viewModel.getChosenGenres()
        genres.genres.forEach{
            it.isChecked = chosenGenres.contains(it)
        }
        genresListAdapter.setGenresInfo(genres.genres)
    }
}