package com.soldatov.vkino.presentation.ui.favorite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.soldatov.domain.models.favorite.Categories

class FavViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private lateinit var categories: List<Categories>

    fun setPages(category: List<Categories>){
        categories = category
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (categories[position]){
            Categories.FILMS -> {
                ViewFavFilmsFragment()
            }
            Categories.ANIME -> {
                ViewFavAnimeFragment()
            }
            Categories.SERIALS -> {
                ViewFavSerialsFragment()
            }
            Categories.ANIME_SERIALS -> {
                ViewFavAnimeSerialsFragment()
            }
            Categories.TV -> {
                ViewFavTvFragment()
            }
        }
    }
}