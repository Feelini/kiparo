package com.soldatov.vkino.presentation.ui.film

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.soldatov.domain.models.DomainTopSliderInfo
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
            setupUI(viewModel.getFilmById(filmId))
        }
    }

    private fun setupUI(film: DomainTopSliderInfo?) {
        binding.filmName.text = getFilmTitle(film)
        Picasso.with(context).load(film?.poster).into(binding.filmPoster)
        binding.filmRatingValue.text = film?.rating.toString()
        binding.filmCategoryValue.text = film?.category
        if (film?.genres != null) {
            binding.filmGenreValue.text = Helper.listToString(film.genres)
        }
        binding.filmYearValue.text = film?.year.toString()
        if (film?.qualities != null) {
            binding.filmQualityValue.text = Helper.listToString(film.qualities)
        }
        if (film?.translations != null) {
            binding.filmTranslationValue.text = Helper.listToString(film.translations)
        }
        if (film?.countries != null){
            binding.filmCountryValue.text = Helper.listToString(film.countries)
        }
        binding.filmDurationValue.text = film?.duration
        binding.filmDescription.text = film?.description

        binding.showMoreBtn.setOnClickListener {
            val dialog = FilmMoreInfoDialogFragment(film)
            val dialogWindow = dialog.show(childFragmentManager, "Dialog")
            dialogWindow
        }
    }

    private fun getFilmTitle(film: DomainTopSliderInfo?): String{
        return if (film?.title != null){
            if (film.year != null){
                "${film.title} (${film.year})"
            } else film.title!!
        } else ""
    }
}