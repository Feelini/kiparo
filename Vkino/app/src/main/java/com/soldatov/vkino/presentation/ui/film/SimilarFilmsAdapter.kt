package com.soldatov.vkino.presentation.ui.film

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.FilmInfo
import com.soldatov.vkino.databinding.ItemSimilarFilmsBinding
import com.soldatov.vkino.presentation.utils.getFilmTitle
import com.squareup.picasso.Picasso

class SimilarFilmsAdapter : RecyclerView.Adapter<SimilarFilmsAdapter.SimilarFilmsViewHolder>() {

    private var filmsList: List<FilmInfo> = ArrayList()
    private lateinit var onFilmClickListener: OnFilmClickListener

    interface OnFilmClickListener {
        fun onSimilarFilmClick(filmId: Long)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSimilarFilmsInfo(
        filmList: List<FilmInfo>,
        gettingOnFilmClickListener: OnFilmClickListener
    ) {
        filmsList = filmList
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

        fun bindData(filmInfo: FilmInfo, onFilmClickListener: OnFilmClickListener) {
            binding.filmName.text = getFilmTitle(filmInfo)
            Picasso.with(itemView.context).load(filmInfo.poster).into(binding.poster)
            itemView.setOnClickListener {
                onFilmClickListener.onSimilarFilmClick(filmInfo.filmId)
            }
        }
    }
}