package com.soldatov.vkino.presentation.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Country
import com.soldatov.domain.models.result.CountriesResult
import com.soldatov.vkino.databinding.FragmentChooseCountryBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChooseCountryFragment : Fragment() {

    private lateinit var binding: FragmentChooseCountryBinding
    private val countriesListAdapter = CountriesListAdapter()
    private val viewModel by sharedViewModel<FilterFragmentViewModel>()
    private var loading = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.countriesList.adapter = countriesListAdapter
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.countriesList.layoutManager =layoutManager

        binding.searchCountry.setQuery(viewModel.getCountriesSearchQuery(), false)

        binding.searchCountry.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchCountry.clearFocus()
                val searchQuery = binding.searchCountry.query.toString()
                val chosenCountries = viewModel.getChosenCountries()
                viewModel.setCountriesSearchQuery(searchQuery)
                countriesListAdapter.setSearchCountries(emptyList(), chosenCountries)
                binding.preloadLayout.visibility = View.VISIBLE
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == ""){
                    val chosenCountries = viewModel.getChosenCountries()
                    viewModel.setCountriesSearchQuery("")
                    countriesListAdapter.setSearchCountries(emptyList(), chosenCountries)
                    binding.preloadLayout.visibility = View.VISIBLE
                }
                return true
            }

        })

        binding.countriesList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0){
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (loading && ((visibleItemCount + pastVisibleItems) >= totalItemCount)){
                        loading = false
                        viewModel.countriesNextPage()
                    }
                }
            }
        })

        binding.submit.setOnClickListener {
            val chosenCountries = countriesListAdapter.getChosenCountries()
            viewModel.setChosenCountries(chosenCountries)
            findNavController().popBackStack()
        }

        setupObservers()
    }

    private fun setupObservers(){
        viewModel.searchCounties.observe(viewLifecycleOwner, {
            when (it){
                is CountriesResult.Success -> {
                    showCountriesList(it.data.countries)
                    if (it.data.hasMore){
                        countriesListAdapter.addProgress()
                        loading = true
                    } else {
                        countriesListAdapter.removeProgress()
                    }
                    binding.preloadLayout.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun showCountriesList(countries: List<Country>){
        val chosenCountries = viewModel.getChosenCountries()
        countriesListAdapter.setSearchCountries(countries, chosenCountries)
    }
}