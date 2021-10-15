package com.soldatov.vkino.presentation.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.data.api.request_status.GenresResult
import com.soldatov.domain.models.GenresList
import com.soldatov.vkino.databinding.FragmentChooseGenreBinding
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
        genresListAdapter.setGenresInfo(genres.genres)
    }
}