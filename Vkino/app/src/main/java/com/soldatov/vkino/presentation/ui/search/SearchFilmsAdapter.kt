package com.soldatov.vkino.presentation.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.FilmInfo
import com.soldatov.vkino.databinding.ItemHomePageFilmBinding
import com.soldatov.vkino.presentation.utils.getFilmTitle
import com.soldatov.vkino.presentation.utils.listToString
import com.squareup.picasso.Picasso

class SearchFilmsAdapter : RecyclerView.Adapter<SearchFilmsAdapter.SearchFilmsViewHolder>() {

    private var filmsList: List<FilmInfo> = ArrayList()
    private lateinit var onFilmClickListener: OnFilmClickListener

    interface OnFilmClickListener {
        fun onSearchFilmClick(filmId: Long)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSearchFilms(
        filmList: List<FilmInfo>,
        gettingOnFilmClickListener: OnFilmClickListener
    ) {
        filmsList = filmList
        onFilmClickListener = gettingOnFilmClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFilmsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomePageFilmBinding.inflate(inflater, parent, false)
        return SearchFilmsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchFilmsViewHolder, position: Int) {
        holder.bindData(filmsList[position], onFilmClickListener)
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    class SearchFilmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemHomePageFilmBinding.bind(itemView)

        fun bindData(filmInfo: FilmInfo, onFilmClickListener: OnFilmClickListener) {
            binding.filmTitle.text = getFilmTitle(filmInfo)
            binding.filmRating.text = filmInfo.rating.toString()
            binding.filmCategoryValue.text = filmInfo.category
            binding.filmGenreValue.text = listToString(filmInfo.genres)
            binding.filmDescription.text = filmInfo.description
            Picasso.with(itemView.context).load(filmInfo.poster).into(binding.filmPoster)
            itemView.setOnClickListener {
                onFilmClickListener.onSearchFilmClick(filmInfo.filmId)
            }
        }
    }
}