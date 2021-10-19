package com.soldatov.vkino.presentation.ui.filter.actor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Actor
import com.soldatov.domain.models.result.ActorsResult
import com.soldatov.vkino.databinding.FragmentChooseActorBinding
import com.soldatov.vkino.presentation.ui.filter.FilterFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChooseActorFragment: Fragment() {

    private lateinit var binding: FragmentChooseActorBinding
    private val actorsListAdapter = ActorsListAdapter()
    private val viewModel by sharedViewModel<FilterFragmentViewModel>()
    private var loading = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseActorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actorsList.adapter = actorsListAdapter
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.actorsList.layoutManager = layoutManager

        binding.searchActor.setQuery(viewModel.getActorsSearchQuery(), false)
        binding.searchActor.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchActor.clearFocus()
                val searchQuery = binding.searchActor.query.toString()
                val chosenActors = viewModel.getChosenActors()
                viewModel.setActorsSearchQuery(searchQuery)
                actorsListAdapter.setSearchActors(emptyList(), chosenActors)
                binding.preloadLayout.visibility = View.VISIBLE
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == ""){
                    val chosenActors = viewModel.getChosenActors()
                    viewModel.setActorsSearchQuery("")
                    actorsListAdapter.setSearchActors(emptyList(), chosenActors)
                    binding.preloadLayout.visibility = View.VISIBLE
                }
                return true
            }
        })

        binding.actorsList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0){
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (loading && ((visibleItemCount + pastVisibleItems) >= totalItemCount)){
                        loading = false
                        viewModel.actorsNextPage()
                    }
                }
            }
        })

        binding.submit.setOnClickListener {
            val chosenActors = actorsListAdapter.getChosenActors()
            viewModel.setChosenActors(chosenActors)
            findNavController().popBackStack()
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.searchActors.observe(viewLifecycleOwner, {
            when (it){
                is ActorsResult.Success -> {
                    showActorsList(it.data.actors)
                    if (it.data.hasMore){
                        actorsListAdapter.addProgress()
                        loading = true
                    } else {
                        actorsListAdapter.removeProgress()
                    }
                    binding.preloadLayout.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun showActorsList(actors: List<Actor>) {
        val chosenActors = viewModel.getChosenActors()
        actorsListAdapter.setSearchActors(actors, chosenActors)
    }
}