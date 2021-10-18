package com.soldatov.vkino.presentation.ui.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Category
import com.soldatov.vkino.databinding.ItemFilterListBinding

class CategoriesListAdapter: RecyclerView.Adapter<CategoriesListAdapter.CategoriesListViewHolder>(){

    private var categoriesList: List<Category> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setCategoriesInfo(newGenres: List<Category>){
        categoriesList = newGenres
        notifyDataSetChanged()
    }

    fun getChosenCategories(): List<Category>{
        return categoriesList.filter { it.isChecked }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterListBinding.inflate(inflater, parent, false)
        return CategoriesListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CategoriesListViewHolder, position: Int) {
        holder.bindData(categoriesList[position])
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    class CategoriesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = ItemFilterListBinding.bind(itemView)

        fun bindData(category: Category){
            binding.filterTitle.text = category.name
            binding.checkbox.isChecked = category.isChecked
            itemView.setOnClickListener {
                if (category.isChecked){
                    category.isChecked = false
                    binding.checkbox.isChecked = false
                } else {
                    category.isChecked = true
                    binding.checkbox.isChecked = true
                }
            }
        }
    }
}