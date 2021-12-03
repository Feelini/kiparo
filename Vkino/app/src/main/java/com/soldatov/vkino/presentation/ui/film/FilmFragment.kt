package com.soldatov.vkino.presentation.ui.film

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.soldatov.domain.models.result.FilmResult
import com.soldatov.domain.models.result.FilmsSliderResult
import com.soldatov.data.repository.FILM_SLIDER_MODE
import com.soldatov.domain.models.FilmInfo
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.FragmentFilmBinding
import com.soldatov.vkino.presentation.utils.getFilmTitle
import com.soldatov.vkino.presentation.utils.listToString
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

const val FILM_ID_KEY = "com.soldatov.vkino.presentation.ui.film.FILM_ID_KEY"
const val FILM_MODE_KEY = "com.soldatov.vkino.presentation.ui.film.FILM_MODE_KEY"

class FilmFragment : Fragment(), SimilarFilmsAdapter.OnFilmClickListener {

    private lateinit var binding: FragmentFilmBinding
    private val viewModel by sharedViewModel<FilmFragmentViewModel>()
    private val similarFilmsAdapter = SimilarFilmsAdapter()
    private var filmId: Long? = null
    private var filmMode: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        filmId = arguments?.getLong(FILM_ID_KEY)
        filmMode = arguments?.getString(FILM_MODE_KEY) ?: ""
        filmId.let {
            if (it != null){
                viewModel.setFilmId(it)
            }
        }
        viewModel.setFilmMode(filmMode)
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
        filmId.let {
            viewModel.similarFilms.observe(viewLifecycleOwner, {
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

            viewModel.filmById.observe(viewLifecycleOwner, {
                when (it) {
                    is FilmResult.Success -> {
                        showFilmInfo(it.data)
                    }
                    is FilmResult.Error -> {

                    }
                    FilmResult.Loading -> {

                    }
                }
            })
        }
    }

    private fun showFilmInfo(film: FilmInfo) {
        binding.filmName.text = getFilmTitle(film)
        Picasso.with(context).load(film.poster).into(binding.filmPoster)
        binding.filmRatingValue.text = film.rating.toString()
        binding.filmCategoryValue.text = film.category
        if (film.genres.isNotEmpty()) {
            binding.filmGenreValue.text = listToString(film.genres)
        } else {
            binding.filmGenreLayout.visibility = View.GONE
        }
        if (film.year != null) {
            binding.filmYearValue.text = film.year.toString()
        } else {
            binding.filmYearLayout.visibility = View.GONE
        }
        if (film.qualities.isNotEmpty()) {
            binding.filmQualityValue.text = listToString(film.qualities)
        } else {
            binding.filmQualityLayout.visibility = View.GONE
        }
        if (film.translations.isNotEmpty()) {
            binding.filmTranslationValue.text = listToString(film.translations)
        } else {
            binding.filmTranslationLayout.visibility = View.GONE
        }
        if (film.countries.isNotEmpty()) {
            binding.filmCountryValue.text = listToString(film.countries)
        } else {
            binding.filmCountryLayout.visibility = View.GONE
        }
        if (film.duration != "") {
            binding.filmDurationValue.text = film.duration
        } else {
            binding.filmDurationLayout.visibility = View.GONE
        }
        if (film.description != "") {
            binding.filmDescription.text = film.description
        } else {
            binding.filmDescription.visibility = View.GONE
        }

        binding.showMoreBtn.setOnClickListener {
            val dialog = FilmMoreInfoDialogFragment(film)
            dialog.show(childFragmentManager, "Dialog")
        }

        showTabs(film.iframeSrc, film.trailer)
    }

    private fun showSimilarFilmsList(filmsList: List<FilmInfo>) {
        similarFilmsAdapter.setSimilarFilmsInfo(filmsList, this)
    }

    private fun showTabs(iframeSrc: String?, iframeTrailer: String?) {

        val tabsTitle = listOf("Смотреть", "Трейлер")
        val viewPagerAdapter = ViewPagerAdapter(this)

        viewPagerAdapter.setIframe(iframeSrc, iframeTrailer)
        binding.viewPagerWatch.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabWatch, binding.viewPagerWatch) { tab, position ->
            tab.text = tabsTitle[position]
        }.attach()
    }

    override fun onSimilarFilmClick(filmId: Long) {
        findNavController().navigate(
            R.id.action_filmFragment_self,
            bundleOf(FILM_ID_KEY to filmId, FILM_MODE_KEY to FILM_SLIDER_MODE)
        )
    }
}