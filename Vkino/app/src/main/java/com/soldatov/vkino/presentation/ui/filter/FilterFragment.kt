package com.soldatov.vkino.presentation.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.RangeSlider
import com.soldatov.domain.models.Category
import com.soldatov.domain.models.result.YearsResult
import com.soldatov.domain.models.Genre
import com.soldatov.domain.models.Years
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.FragmentFilterBinding
import com.soldatov.vkino.presentation.ui.home.HomeFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.time.Year

class FilterFragment : Fragment(), ChosenGenresListAdapter.OnRemoveChosenGenreListener,
    ChosenCategoryListAdapter.OnRemoveChosenCategoryListener {

    private lateinit var binding: FragmentFilterBinding
    private val chosenGenresListAdapter = ChosenGenresListAdapter()
    private val chosenCategoriesListAdapter = ChosenCategoryListAdapter()
    private val homeFragmentViewModel by sharedViewModel<HomeFragmentViewModel>()
    private val filterFragmentViewModel by sharedViewModel<FilterFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()

        binding.editYearFrom.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_NEXT -> {
                    val years = filterFragmentViewModel.getYears()
                    val currentMaxYearString = binding.editYearTo.text.toString()
                    if (currentMaxYearString != "") {
                        val currentMaxYear = currentMaxYearString.toInt()
                        when {
                            binding.editYearFrom.text.toString().toInt() < years.min -> {
                                binding.editYearFrom.setText(years.min.toString())
                                binding.yearRange.values =
                                    listOf(years.min.toFloat(), years.max.toFloat())
                            }
                            binding.editYearFrom.text.toString().toInt() > currentMaxYear -> {
                                binding.editYearFrom.setText(currentMaxYear.toString())
                                binding.yearRange.values =
                                    listOf(currentMaxYear.toFloat(), currentMaxYear.toFloat())
                            }
                            else -> {
                                binding.yearRange.values = listOf(
                                    binding.editYearFrom.text.toString().toFloat(),
                                    currentMaxYear.toFloat()
                                )
                            }
                        }
                    }
                    false
                }
                else -> {
                    false
                }
            }
        }

        binding.editYearTo.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val years = filterFragmentViewModel.getYears()
                    val currentMinYearString = binding.editYearFrom.text.toString()
                    if (currentMinYearString != "") {
                        val currentMinYear = currentMinYearString.toInt()
                        when {
                            binding.editYearTo.text.toString().toInt() < currentMinYear -> {
                                binding.editYearTo.setText(currentMinYear.toString())
                                binding.yearRange.values =
                                    listOf(currentMinYear.toFloat(), currentMinYear.toFloat())
                            }
                            binding.editYearTo.text.toString().toInt() > years.max -> {
                                binding.editYearTo.setText(years.max.toString())
                                binding.yearRange.values =
                                    listOf(currentMinYear.toFloat(), years.max.toFloat())
                            }
                            else -> {
                                binding.yearRange.values = listOf(
                                    currentMinYear.toFloat(),
                                    binding.editYearTo.text.toString().toFloat()
                                )
                            }
                        }
                    }
                    binding.editYearTo.clearFocus()
                    false
                }
                else -> {
                    false
                }
            }
        }

        binding.yearRange.addOnChangeListener { slider, value, fromUser ->
            binding.editYearFrom.setText(slider.values[0].toInt().toString())
            binding.editYearTo.setText(slider.values[1].toInt().toString())
        }

        binding.yearRange.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {

            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val chosenYears = Years(slider.values[0].toInt(), slider.values[1].toInt())
                filterFragmentViewModel.setChosenYears(chosenYears)
            }

        })

        binding.genre.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_chooseGenreFragment)
        }
        binding.category.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_chooseCategoryFragment)
        }

        binding.chosenGenresList.adapter = chosenGenresListAdapter
        binding.chosenGenresList.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.chosenCategoriesList.adapter = chosenCategoriesListAdapter
        binding.chosenCategoriesList.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun setupObservers() {
        filterFragmentViewModel.years.observe(viewLifecycleOwner, {
            when (it) {
                is YearsResult.Success -> {
                    showYears(it.data)
                }
            }
        })
        filterFragmentViewModel.chosenGenres.observe(viewLifecycleOwner, {
            showChosenGenresList(it)
        })
        filterFragmentViewModel.chosenCategories.observe(viewLifecycleOwner, {
            showChosenCategoriesList(it)
        })
        filterFragmentViewModel.chosenYears.observe(viewLifecycleOwner, {
            setChosenYears(it)
        })
    }

    private fun showYears(years: Years) {
        binding.editYearFrom.setText(years.min.toString())
        binding.editYearTo.setText(years.max.toString())

        binding.yearRange.valueFrom = years.min.toFloat()
        binding.yearRange.valueTo = years.max.toFloat()
        binding.yearRange.stepSize = 1f
        binding.yearRange.values = listOf(years.min.toFloat(), years.max.toFloat())
    }

    private fun showChosenGenresList(genres: List<Genre>) {
        if (genres.isEmpty()) {
            binding.chosenGenresLayout.visibility = View.GONE
        } else {
            binding.chosenGenresLayout.visibility = View.VISIBLE
            chosenGenresListAdapter.setChosenGenresInfo(genres, this)
        }
    }

    private fun showChosenCategoriesList(categories: List<Category>) {
        if (categories.isEmpty()) {
            binding.chosenCategoriesLayout.visibility = View.GONE
        } else {
            binding.chosenCategoriesLayout.visibility = View.VISIBLE
            chosenCategoriesListAdapter.setChosenCategoriesInfo(categories, this)
        }
    }

    private fun setChosenYears(years: Years){
        binding.editYearFrom.setText(years.min.toString())
        binding.editYearTo.setText(years.max.toString())

        binding.yearRange.values = listOf(years.min.toFloat(), years.max.toFloat())
    }

    override fun onRemoveChosenGenre(genre: Genre) {
        filterFragmentViewModel.removeChosenGenre(genre)
    }

    override fun onRemoveChosenCategory(category: Category) {
        filterFragmentViewModel.removeChosenCategories(category)
    }
}