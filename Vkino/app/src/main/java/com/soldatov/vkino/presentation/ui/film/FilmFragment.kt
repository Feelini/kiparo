package com.soldatov.vkino.presentation.ui.film

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.soldatov.data.api.FilmsSliderResult
import com.soldatov.domain.models.DomainFilmSliderInfo
import com.soldatov.vkino.databinding.FragmentFilmBinding
import com.soldatov.vkino.presentation.utils.Helper
import com.soldatov.vkino.presentation.utils.Helper.getFilmTitle
import com.soldatov.vkino.presentation.viewmodel.FilmFragmentViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val FILM_ID_KEY = "com.soldatov.vkino.presentation.ui.film.FILM_ID_KEY"

class FilmFragment : Fragment() {

    private lateinit var binding: FragmentFilmBinding
    private val viewModel by sharedViewModel<FilmFragmentViewModel>()
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val similarFilmsAdapter = SimilarFilmsAdapter()
    private var filmId: Long? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewPagerAdapter = ViewPagerAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        filmId = arguments?.getLong(FILM_ID_KEY)
        binding = FragmentFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        binding.similarFilms.adapter = similarFilmsAdapter
        binding.similarFilms.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    private fun setupObservers() {
        filmId.let { filmId ->
            viewModel.getSimilarFilms(filmId!!).observe(viewLifecycleOwner, {
                when (it) {
                    is FilmsSliderResult.Success -> {
                        showSimilarFilmsList(it.data)
                    }
                    is FilmsSliderResult.Error -> {

                    }
                    FilmsSliderResult.Loading -> {

                    }
                }
            })
            viewModel.getFilmById(filmId).observe(viewLifecycleOwner, {
                setupUI(it)
            })
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
        if (film?.countries != null) {
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

    private fun showSimilarFilmsList(filmsList: List<DomainFilmSliderInfo>) {
        similarFilmsAdapter.setSimilarFilmsInfo(filmsList, findNavController())
    }

    private fun showTabs(iframeSrc: String?, iframeTrailer: String?) {

        val tabsTitle = listOf("Смотреть", "Трейлер")

        viewPagerAdapter.setIframe(iframeSrc, iframeTrailer)
        binding.viewPagerWatch.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabWatch, binding.viewPagerWatch) { tab, position ->
            tab.text = tabsTitle[position]
        }.attach()
    }
}