package com.soldatov.vkino.presentation.ui.film

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.FilmSliderInfo
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.ItemSimilarFilmsBinding
import com.soldatov.vkino.presentation.utils.getFilmTitle
import com.squareup.picasso.Picasso

class SimilarFilmsAdapter : RecyclerView.Adapter<SimilarFilmsAdapter.SimilarFilmsViewHolder>() {

    private var filmsList: List<FilmSliderInfo> = ArrayList()
    private lateinit var onFilmClickListener: OnFilmClickListener

    interface OnFilmClickListener {
        fun onSimilarFilmClick(filmId: Long)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSimilarFilmsInfo(
        filmSliderList: List<FilmSliderInfo>,
        gettingOnFilmClickListener: OnFilmClickListener
    ) {
        filmsList = filmSliderList
        onFilmClickListener = gettingOnFilmClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarFilmsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSimilarFilmsBinding.inflate(inflater, parent, false)
        return SimilarFilmsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SimilarFilmsViewHolder, position: Int) {
        holder.bindData(filmsList[position], onFilmClickListener)
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    class SimilarFilmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemSimilarFilmsBinding.bind(itemView)

        fun bindData(filmSliderInfo: FilmSliderInfo, onFilmClickListener: OnFilmClickListener) {
            binding.filmName.text = getFilmTitle(filmSliderInfo)
            Picasso.with(itemView.context).load(filmSliderInfo.poster).into(binding.poster)
            itemView.setOnClickListener {
                onFilmClickListener.onSimilarFilmClick(filmSliderInfo.filmId)
            }
        }
    }
}