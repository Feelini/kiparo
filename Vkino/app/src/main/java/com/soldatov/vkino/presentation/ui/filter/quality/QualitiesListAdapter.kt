package com.soldatov.vkino.presentation.ui.filter.quality

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Quality
import com.soldatov.vkino.databinding.ItemFilterListBinding

class QualitiesListAdapter: RecyclerView.Adapter<QualitiesListAdapter.QualitiesListViewHolder>() {

    private var qualitiesList: List<Quality> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setQualities(newQualities: List<Quality>){
        qualitiesList = newQualities
        notifyDataSetChanged()
    }

    fun getChosenQualities(): List<Quality>{
        return qualitiesList.filter { it.isChecked }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QualitiesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterListBinding.inflate(inflater, parent, false)
        return QualitiesListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: QualitiesListViewHolder, position: Int) {
        holder.bindData(qualitiesList[position])
    }

    override fun getItemCount(): Int {
        return qualitiesList.size
    }

    class QualitiesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = ItemFilterListBinding.bind(itemView)

        fun bindData(quality: Quality){
            binding.filterTitle.text = quality.name
            binding.checkbox.isChecked = quality.isChecked
            itemView.setOnClickListener {
                if (quality.isChecked){
                    quality.isChecked = false
                    binding.checkbox.isChecked = false
                } else {
                    quality.isChecked = true
                    binding.checkbox.isChecked = true
                }
            }
        }
    }
}