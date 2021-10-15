package com.soldatov.vkino.presentation.ui.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Genre
import com.soldatov.vkino.databinding.ItemGenresListBinding

class GenresListAdapter: RecyclerView.Adapter<GenresListAdapter.GenresListViewHolder>() {

    private var genresList: List<Genre> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setGenresInfo(newGenres: List<Genre>){
        genresList = newGenres
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGenresListBinding.inflate(inflater, parent, false)
        return GenresListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: GenresListViewHolder, position: Int) {
        holder.bindData(genresList[position])
    }

    override fun getItemCount(): Int {
        return genresList.size
    }

    class GenresListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = ItemGenresListBinding.bind(itemView)

        fun bindData(genre: Genre){
            binding.genreTitle.text = genre.name
            binding.checkbox.isChecked = genre.isChecked
            itemView.setOnClickListener {
                if (genre.isChecked){
                    genre.isChecked = false
                    binding.checkbox.isChecked = false
                } else {
                    genre.isChecked = true
                    binding.checkbox.isChecked = true
                }
            }
        }
    }
}