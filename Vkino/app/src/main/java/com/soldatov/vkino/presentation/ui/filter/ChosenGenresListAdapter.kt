package com.soldatov.vkino.presentation.ui.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Genre
import com.soldatov.vkino.databinding.ItemChosenListBinding

class ChosenGenresListAdapter :
    RecyclerView.Adapter<ChosenGenresListAdapter.ChosenGenresListViewHolder>() {

    private var genresList: List<Genre> = ArrayList()
    private lateinit var onRemoveChosenGenre: OnRemoveChosenGenreListener

    interface OnRemoveChosenGenreListener {
        fun onRemoveChosenGenre(genre: Genre)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setChosenGenresInfo(
        newGenres: List<Genre>,
        onRemoveChosenGenreListener: OnRemoveChosenGenreListener
    ) {
        genresList = newGenres
        onRemoveChosenGenre = onRemoveChosenGenreListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChosenGenresListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChosenListBinding.inflate(inflater, parent, false)
        return ChosenGenresListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holderChosen: ChosenGenresListViewHolder, position: Int) {
        holderChosen.bindData(genresList[position], onRemoveChosenGenre)
    }

    override fun getItemCount(): Int {
        return genresList.size
    }

    class ChosenGenresListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemChosenListBinding.bind(itemView)

        fun bindData(genre: Genre, onRemoveChosenGenre: OnRemoveChosenGenreListener) {
            binding.name.text = genre.name
            binding.cancel.setOnClickListener {
                onRemoveChosenGenre.onRemoveChosenGenre(genre)
            }
        }
    }
}