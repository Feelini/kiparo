package com.soldatov.vkino.presentation.ui.filter.category

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Category
import com.soldatov.vkino.databinding.ItemChosenListBinding

class ChosenCategoryListAdapter: RecyclerView.Adapter<ChosenCategoryListAdapter.CategoryListViewHolder>() {

    private var categoriesList: List<Category> = ArrayList()
    private lateinit var onRemoveChosenCategory: OnRemoveChosenCategoryListener

    interface OnRemoveChosenCategoryListener {
        fun onRemoveChosenCategory(category: Category)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setChosenCategoriesInfo(
        newCategories: List<Category>,
        onRemoveChosenCategoryListener: OnRemoveChosenCategoryListener
    ) {
        categoriesList = newCategories
        onRemoveChosenCategory = onRemoveChosenCategoryListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChosenListBinding.inflate(inflater, parent, false)
        return CategoryListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holderChosen: CategoryListViewHolder, position: Int) {
        holderChosen.bindData(categoriesList[position], onRemoveChosenCategory)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    class CategoryListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val binding = ItemChosenListBinding.bind(itemView)

        fun bindData(category: Category, onRemoveChosenCategory: OnRemoveChosenCategoryListener) {
            binding.name.text = category.name
            binding.cancel.setOnClickListener {
                onRemoveChosenCategory.onRemoveChosenCategory(category)
            }
        }
    }
}