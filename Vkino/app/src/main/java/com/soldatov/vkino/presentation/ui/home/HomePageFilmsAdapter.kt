package com.soldatov.vkino.presentation.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.FilmSliderInfo
import com.soldatov.vkino.databinding.ItemHomePageFilmBinding
import com.soldatov.vkino.presentation.utils.getFilmTitle
import com.soldatov.vkino.presentation.utils.listToString
import com.squareup.picasso.Picasso

class HomePageFilmsAdapter : RecyclerView.Adapter<HomePageFilmsAdapter.HomePageFilmsViewHolder>() {

    private var filmsList: List<FilmSliderInfo> = ArrayList()
    private lateinit var onFilmClickListener: OnFilmClickListener

    interface OnFilmClickListener {
        fun onHomeFilmClick(filmId: Long)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setHomePageFilms(
        filmList: List<FilmSliderInfo>,
        gettingOnFilmClickListener: OnFilmClickListener
    ) {
        filmsList = filmList
        onFilmClickListener = gettingOnFilmClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageFilmsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomePageFilmBinding.inflate(inflater, parent, false)
        return HomePageFilmsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HomePageFilmsViewHolder, position: Int) {
        holder.bindData(filmsList[position], onFilmClickListener)
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    class HomePageFilmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemHomePageFilmBinding.bind(itemView)

        fun bindData(filmSliderInfo: FilmSliderInfo, onFilmClickListener: OnFilmClickListener) {
            binding.filmTitle.text = getFilmTitle(filmSliderInfo)
            binding.filmRating.text = filmSliderInfo.rating.toString()
            binding.filmCategoryValue.text = filmSliderInfo.category
            binding.filmGenreValue.text = listToString(filmSliderInfo.genres)
            binding.filmDescription.text = filmSliderInfo.description
            Picasso.with(itemView.context).load(filmSliderInfo.poster).into(binding.filmPoster)
            itemView.setOnClickListener {
                onFilmClickListener.onHomeFilmClick(filmSliderInfo.filmId)
            }
        }
    }
}