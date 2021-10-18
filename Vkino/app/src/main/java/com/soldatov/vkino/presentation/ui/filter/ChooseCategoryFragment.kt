package com.soldatov.vkino.presentation.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Category
import com.soldatov.domain.models.result.CategoriesResult
import com.soldatov.vkino.databinding.FragmentChooseCategoryBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChooseCategoryFragment : Fragment() {

    private lateinit var binding: FragmentChooseCategoryBinding
    private val categoryListAdapter = CategoriesListAdapter()
    private val viewModel by sharedViewModel<FilterFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoriesList.adapter = categoryListAdapter
        binding.categoriesList.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.submit.setOnClickListener {
            val chosenCategories = categoryListAdapter.getChosenCategories()
            viewModel.setChosenCategories(chosenCategories)
            findNavController().popBackStack()
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.categories.observe(viewLifecycleOwner, {
            when (it) {
                is CategoriesResult.Success -> {
                    showCategoriesList(it.data)
                }
            }
        })
    }

    private fun showCategoriesList(categories: List<Category>){
        val chosenCategories = viewModel.getChosenCategories()
        categories.forEach{
            it.isChecked = chosenCategories.contains(it)
        }
        categoryListAdapter.setCategoriesInfo(categories)
    }
}