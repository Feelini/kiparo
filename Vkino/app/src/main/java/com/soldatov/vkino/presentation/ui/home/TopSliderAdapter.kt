package com.soldatov.vkino.presentation.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.FilmSliderInfo
import com.soldatov.vkino.databinding.ItemTopSliderBinding
import com.soldatov.vkino.presentation.utils.listToString
import com.squareup.picasso.Picasso

class TopSliderAdapter : RecyclerView.Adapter<TopSliderAdapter.TopSliderViewHolder>() {

    private var filmsList: List<FilmSliderInfo> = ArrayList()
    private lateinit var onFilmClickListener: OnFilmClickListener

    interface OnFilmClickListener {
        fun onSliderFilmClick(filmId: Long)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTopSliderInfo(
        filmSliderList: List<FilmSliderInfo>,
        gettingOnFilmClickListener: OnFilmClickListener
    ) {
        filmsList = filmSliderList
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

        fun bindData(filmSliderInfo: FilmSliderInfo, onFilmClickListener: OnFilmClickListener) {
            binding.name.text = filmSliderInfo.title
            binding.year.text = filmSliderInfo.year.toString()
            binding.genres.text = listToString(filmSliderInfo.genres)
            Picasso.with(itemView.context).load(filmSliderInfo.poster).into(binding.poster)
            itemView.setOnClickListener {
                onFilmClickListener.onSliderFilmClick(filmSliderInfo.filmId)
            }
        }
    }
}