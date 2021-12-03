package com.soldatov.vkino.presentation.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.FilmInfo
import com.soldatov.vkino.databinding.ItemTopSliderBinding
import com.soldatov.vkino.presentation.utils.listToString
import com.squareup.picasso.Picasso

class TopSliderAdapter : RecyclerView.Adapter<TopSliderAdapter.TopSliderViewHolder>() {

    private var filmsList: List<FilmInfo> = ArrayList()
    private lateinit var onFilmClickListener: OnFilmClickListener

    interface OnFilmClickListener {
        fun onSliderFilmClick(filmId: Long)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTopSliderInfo(
        filmList: List<FilmInfo>,
        gettingOnFilmClickListener: OnFilmClickListener
    ) {
        filmsList = filmList
        onFilmClickListener = gettingOnFilmClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSliderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTopSliderBinding.inflate(inflater, parent, false)
        return TopSliderViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: TopSliderViewHolder, position: Int) {
        holder.bindData(filmsList[position], onFilmClickListener)
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    class TopSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemTopSliderBinding.bind(itemView)

        fun bindData(filmInfo: FilmInfo, onFilmClickListener: OnFilmClickListener) {
            binding.name.text = filmInfo.title
            binding.year.text = filmInfo.year.toString()
            binding.genres.text = listToString(filmInfo.genres)
            Picasso.with(itemView.context).load(filmInfo.poster).into(binding.poster)
            itemView.setOnClickListener {
                onFilmClickListener.onSliderFilmClick(filmInfo.filmId)
            }
        }
    }
}