package com.soldatov.vkino.presentation.ui.film

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.soldatov.domain.models.DomainTopSliderInfo
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.DialogFilmMoreInfoBinding
import com.soldatov.vkino.presentation.utils.Helper

class FilmMoreInfoDialogFragment(private val film: DomainTopSliderInfo?): DialogFragment() {

    private var _binding: DialogFilmMoreInfoBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            _binding = DialogFilmMoreInfoBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(it, R.style.CustomDialog)
            if (film?.actors != null) {
                if (film.actors.size > 1){
                    binding.filmActors.text = "Актеры"
                }
                binding.filmActorsValue.text = Helper.listToString(film.actors)
            } else {
                binding.filmActors.visibility = View.GONE
                binding.filmActorsValue.visibility = View.GONE
            }
            if (film?.composers != null) {
                if (film.composers.size > 1){
                    binding.filmComposer.text = "Композиторы"
                }
                binding.filmComposerValue.text = Helper.listToString(film.composers)
            } else {
                binding.filmComposer.visibility = View.GONE
                binding.filmComposerValue.visibility = View.GONE
            }
            if (film?.directors != null) {
                if (film.directors.size > 1){
                    binding.filmDirector.text = "Продюссеры"
                }
                binding.filmDirectorValue.text = Helper.listToString(film.directors)
            } else {
                binding.filmDirector.visibility = View.GONE
                binding.filmDirectorValue.visibility = View.GONE
            }
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}