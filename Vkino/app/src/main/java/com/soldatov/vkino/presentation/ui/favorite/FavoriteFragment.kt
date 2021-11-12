package com.soldatov.vkino.presentation.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.soldatov.domain.models.favorite.Categories
import com.soldatov.vkino.databinding.FragmentFavoritesBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavoriteFragment: Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by sharedViewModel<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getFavCats().observe(viewLifecycleOwner, {
            showTabs(it)
        })
    }

    private fun showTabs(categories: List<Categories>) {
        val tabsTitle = categories.map { it.value }
        val viewPagerAdapter = FavViewPagerAdapter(this)
        viewPagerAdapter.setPages(categories)
        binding.viewPagerCategories.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabsCategories, binding.viewPagerCategories) { tab, position ->
            tab.text = tabsTitle[position]
        }.attach()
    }
}