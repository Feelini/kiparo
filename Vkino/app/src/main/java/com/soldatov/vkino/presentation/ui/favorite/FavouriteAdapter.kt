package com.soldatov.vkino.presentation.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.FilmInfo
import com.soldatov.vkino.databinding.ItemFavouriteListBinding
import com.soldatov.vkino.databinding.ItemProgressBarBinding
import com.soldatov.vkino.presentation.utils.getFilmTitle
import com.squareup.picasso.Picasso

class FavouriteAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var filmsList: List<FilmInfo> = ArrayList()
    private lateinit var onFilmClickListener: OnFilmClickListener
    private val VIEW_ITEM = 1
    private val VIEW_PROG = 0
    private var isProgress = true

    interface OnFilmClickListener {
        fun onFilmClick(filmId: Long)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilms(
        filmList: List<FilmInfo>,
        gettingOnFilmClickListener: OnFilmClickListener
    ) {
        filmsList = filmList
        onFilmClickListener = gettingOnFilmClickListener
        notifyDataSetChanged()
    }

    fun addProgress(){
        isProgress = true
    }

    fun removeProgress(){
        isProgress = false
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == filmsList.size) VIEW_PROG else VIEW_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        viewHolder = if (viewType == VIEW_ITEM){
            val binding = ItemFavouriteListBinding.inflate(inflater, parent, false)
            FavouriteViewHolder(binding.root)
        } else {
            val binding = ItemProgressBarBinding.inflate(inflater, parent, false)
            ProgressBarViewHolder(binding.root)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FavouriteViewHolder){
            holder.bindData(filmsList[position], onFilmClickListener)
        } else {
            (holder as ProgressBarViewHolder).bindData()
        }
    }

    override fun getItemCount(): Int {
        return if (isProgress){
            if (filmsList.isEmpty()) 0 else filmsList.size + 1
        } else {
            filmsList.size
        }
    }

    class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemFavouriteListBinding.bind(itemView)

        fun bindData(filmInfo: FilmInfo, onFilmClickListener: OnFilmClickListener) {
            binding.name.text = getFilmTitle(filmInfo)
            Picasso.with(itemView.context).load(filmInfo.poster).into(binding.poster)
            itemView.setOnClickListener {
                onFilmClickListener.onFilmClick(filmInfo.filmId)
            }
        }
    }

    class ProgressBarViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemProgressBarBinding.bind(itemView)

        fun bindData(){
            binding.progress.isIndeterminate = true
        }
    }
}