package com.soldatov.vkino.presentation.ui.film

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
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
                binding.filmActorsValue.text = Helper.listToString(film.actors)
            }
            if (film?.composers != null) {
                binding.filmComposerValue.text = Helper.listToString(film.composers)
            }
            if (film?.directors != null) {
                binding.filmDirectorValue.text = Helper.listToString(film.directors)
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