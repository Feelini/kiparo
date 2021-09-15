package com.soldatov.vkino.presentation.ui.film

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.soldatov.domain.models.DomainFilmSliderInfo
import com.soldatov.vkino.databinding.FragmentFilmBinding
import com.soldatov.vkino.presentation.utils.Helper
import com.soldatov.vkino.presentation.viewmodel.MainActivityViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

const val FILM_ID_KEY = "filmIdKey"

class FilmFragment: Fragment() {

    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    private val viewModel by sharedViewModel<MainActivityViewModel>()
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filmId = arguments?.getLong(FILM_ID_KEY)
        if (filmId != null) {
            setupUI(viewModel.getTopSliderFilmById(filmId))
        }
    }

    private fun setupUI(film: DomainFilmSliderInfo?) {
        binding.filmName.text = getFilmTitle(film)
        Picasso.with(context).load(film?.poster).into(binding.filmPoster)
        if (film?.rating != null) {
            binding.filmRatingValue.text = film.rating.toString()
        } else {
            binding.filmRatingLayout.visibility = View.GONE
        }
        binding.filmCategoryValue.text = film?.category
        if (film?.genres != null) {
            binding.filmGenreValue.text = Helper.listToString(film.genres)
        } else {
            binding.filmGenreLayout.visibility = View.GONE
        }
        if (film?.year != null) {
            binding.filmYearValue.text = film.year.toString()
        } else {
            binding.filmYearLayout.visibility = View.GONE
        }
        if (film?.qualities != null) {
            binding.filmQualityValue.text = Helper.listToString(film.qualities)
        } else {
            binding.filmQualityLayout.visibility = View.GONE
        }
        if (film?.translations != null) {
            binding.filmTranslationValue.text = Helper.listToString(film.translations)
        } else {
            binding.filmTranslationLayout.visibility = View.GONE
        }
        if (film?.countries != null){
            binding.filmCountryValue.text = Helper.listToString(film.countries)
        } else {
            binding.filmCountryLayout.visibility = View.GONE
        }
        if (film?.duration != null) {
            binding.filmDurationValue.text = film.duration
        } else {
            binding.filmDurationLayout.visibility = View.GONE
        }
        if (film?.description != null) {
            binding.filmDescription.text = film.description
        } else {
            binding.filmDescription.visibility = View.GONE
        }

        binding.showMoreBtn.setOnClickListener {
            val dialog = FilmMoreInfoDialogFragment(film)
            dialog.show(childFragmentManager, "Dialog")
        }

        showTabs(film?.iframeSrc, film?.trailer)
    }

    private fun showTabs(iframeSrc: String?, iframeTrailer: String?){

        val tabsTitle = listOf("Смотреть", "Трейлер")

        viewPagerAdapter = ViewPagerAdapter(this, iframeSrc, iframeTrailer)
        binding.viewPagerWatch.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabWatch, binding.viewPagerWatch) {tab, position ->
            tab.text = tabsTitle[position]
        }.attach()
    }

    private fun getFilmTitle(film: DomainFilmSliderInfo?): String{
        return if (film?.title != null){
            if (film.year != null){
                "${film.title} (${film.year})"
            } else film.title!!
        } else ""
    }
}