package com.soldatov.vkino.presentation.ui.home

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.FilmInfo
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.ItemHomePageFilmBinding
import com.soldatov.vkino.databinding.ItemProgressBarBinding
import com.soldatov.vkino.presentation.utils.getFilmTitle
import com.soldatov.vkino.presentation.utils.listToString
import com.squareup.picasso.Picasso

class HomePageFilmsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var filmsList: List<FilmInfo> = ArrayList()
    private lateinit var onFilmClickListener: OnFilmClickListener
    private lateinit var onAddToFavouriteClickListener: OnAddToFavouriteClickListener
    private val VIEW_ITEM = 1
    private val VIEW_PROG = 0
    private var isProgress = true

    interface OnFilmClickListener {
        fun onHomeFilmClick(filmId: Long)
    }

    interface OnAddToFavouriteClickListener {
        fun onAddToFavoriteClick(filmId: Long, itemPosition: Int, category: String)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setHomePageFilms(
        filmList: List<FilmInfo>,
        gettingOnFilmClickListener: OnFilmClickListener,
        gettingOnAddToFavouriteClickListener: OnAddToFavouriteClickListener
    ) {
        filmsList = filmList
        onFilmClickListener = gettingOnFilmClickListener
        onAddToFavouriteClickListener = gettingOnAddToFavouriteClickListener
        notifyDataSetChanged()
    }

    fun addProgress() {
        isProgress = true
    }

    fun removeProgress() {
        isProgress = false
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addToFavorite(position: Int) {
        filmsList[position].isFavourite = true
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == filmsList.size) VIEW_PROG else VIEW_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        viewHolder = if (viewType == VIEW_ITEM) {
            val binding = ItemHomePageFilmBinding.inflate(inflater, parent, false)
            HomePageFilmsViewHolder(binding.root)
        } else {
            val binding = ItemProgressBarBinding.inflate(inflater, parent, false)
            ProgressBarViewHolder(binding.root)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HomePageFilmsViewHolder) {
            holder.bindData(
                filmsList[position],
                onFilmClickListener,
                onAddToFavouriteClickListener,
                position
            )
        } else {
            (holder as ProgressBarViewHolder).bindData()
        }
    }

    override fun getItemCount(): Int {
        return if (isProgress) {
            if (filmsList.isEmpty()) 0 else filmsList.size + 1
        } else {
            filmsList.size
        }
    }

    class HomePageFilmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemHomePageFilmBinding.bind(itemView)

        @SuppressLint("ResourceAsColor", "ResourceType")
        fun bindData(
            filmInfo: FilmInfo,
            onFilmClickListener: OnFilmClickListener,
            onAddToFavouriteClickListener: OnAddToFavouriteClickListener,
            position: Int
        ) {
            binding.filmTitle.text = getFilmTitle(filmInfo)
            binding.filmRating.text = filmInfo.rating.toString()
            binding.filmCategoryValue.text = filmInfo.category
            binding.filmGenreValue.text = listToString(filmInfo.genres)
            binding.filmDescription.text = filmInfo.description
            if (filmInfo.isFavourite) {
                binding.filmInFavourite.setImageResource(R.drawable.favorite)
            } else {
                binding.filmInFavourite.setImageResource(R.drawable.favorite_border)
            }
            Picasso.with(itemView.context).load(filmInfo.poster).into(binding.filmPoster)
            itemView.setOnClickListener {
                onFilmClickListener.onHomeFilmClick(filmInfo.filmId)
            }
            binding.filmInFavourite.setOnClickListener {
                onAddToFavouriteClickListener.onAddToFavoriteClick(filmInfo.filmId, position, filmInfo.category)
            }
        }
    }

    class ProgressBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemProgressBarBinding.bind(itemView)

        fun bindData() {
            binding.progress.isIndeterminate = true
        }
    }
}