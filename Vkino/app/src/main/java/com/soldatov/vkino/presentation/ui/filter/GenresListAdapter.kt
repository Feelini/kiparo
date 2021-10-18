package com.soldatov.vkino.presentation.ui.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Genre
import com.soldatov.vkino.databinding.ItemFilterListBinding

class GenresListAdapter: RecyclerView.Adapter<GenresListAdapter.GenresListViewHolder>(), Filterable {

    private var genresList: List<Genre> = ArrayList()
    private var genresFilterList: ArrayList<Genre> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setGenresInfo(newGenres: List<Genre>){
        genresList = newGenres
        genresFilterList.addAll(newGenres)
        notifyDataSetChanged()
    }

    fun getChosenGenres(): List<Genre>{
        return genresList.filter { it.isChecked }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterListBinding.inflate(inflater, parent, false)
        return GenresListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: GenresListViewHolder, position: Int) {
        holder.bindData(genresFilterList[position], genresList)
    }

    override fun getItemCount(): Int {
        return genresFilterList.size
    }

    class GenresListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = ItemFilterListBinding.bind(itemView)

        fun bindData(genreFilter: Genre, genres: List<Genre>){
            binding.filterTitle.text = genreFilter.name
            binding.checkbox.isChecked = genreFilter.isChecked
            itemView.setOnClickListener {
                if (genreFilter.isChecked){
                    genres.filter { it.id == genreFilter.id }.map { it.isChecked = false }
                    genreFilter.isChecked = false
                    binding.checkbox.isChecked = false
                } else {
                    genres.filter { it.id == genreFilter.id }.map { it.isChecked = true }
                    genreFilter.isChecked = true
                    binding.checkbox.isChecked = true
                }
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                genresFilterList = if (charSearch.isEmpty()) {
                    genresList as ArrayList<Genre>
                } else {
                    val resultList = ArrayList<Genre>()
                    for (row in genresList) {
                        if (row.name.lowercase().contains(charSearch.lowercase())) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = genresFilterList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                genresFilterList = results?.values as ArrayList<Genre>
                notifyDataSetChanged()
            }
        }
    }
}