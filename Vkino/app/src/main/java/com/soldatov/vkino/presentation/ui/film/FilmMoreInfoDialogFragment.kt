package com.soldatov.vkino.presentation.ui.film

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.soldatov.domain.models.FilmSliderInfo
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.DialogFilmMoreInfoBinding
import com.soldatov.vkino.presentation.utils.listToString

class FilmMoreInfoDialogFragment(private val film: FilmSliderInfo?): DialogFragment() {

    private lateinit var binding: DialogFilmMoreInfoBinding

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            binding = DialogFilmMoreInfoBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(it, R.style.CustomDialog)
            if (film?.actors?.isEmpty() == true) {
                binding.filmActors.visibility = View.GONE
                binding.filmActorsValue.visibility = View.GONE
            } else {
                if (film?.actors?.size!! > 1){
                    binding.filmActors.text = "Актеры"
                }
                binding.filmActorsValue.text = listToString(film.actors)
            }
            if (film.composers.isNotEmpty()) {
                if (film.composers.size > 1){
                    binding.filmComposer.text = "Композиторы"
                }
                binding.filmComposerValue.text = listToString(film.composers)
            } else {
                binding.filmComposer.visibility = View.GONE
                binding.filmComposerValue.visibility = View.GONE
            }
            if (film.directors.isNotEmpty()) {
                if (film.directors.size > 1){
                    binding.filmDirector.text = "Продюссеры"
                }
                binding.filmDirectorValue.text = listToString(film.directors)
            } else {
                binding.filmDirector.visibility = View.GONE
                binding.filmDirectorValue.visibility = View.GONE
            }
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}