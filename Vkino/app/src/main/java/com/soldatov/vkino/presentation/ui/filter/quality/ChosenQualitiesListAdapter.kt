package com.soldatov.vkino.presentation.ui.filter.quality

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Quality
import com.soldatov.vkino.databinding.ItemChosenListBinding

class ChosenQualitiesListAdapter: RecyclerView.Adapter<ChosenQualitiesListAdapter.ChosenQualitiesListViewHolder>() {

    private var qualitiesList: List<Quality> = ArrayList()
    private lateinit var onRemoveChosenQuality: OnRemoveChosenQualityListener

    interface OnRemoveChosenQualityListener{
        fun onRemoveChosenQuality(quality: Quality)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setChosenQualities(
        newQualities: List<Quality>,
        onRemoveChosenQualityListener: OnRemoveChosenQualityListener
    ){
        qualitiesList = newQualities
        onRemoveChosenQuality = onRemoveChosenQualityListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChosenQualitiesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChosenListBinding.inflate(inflater, parent, false)
        return ChosenQualitiesListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ChosenQualitiesListViewHolder, position: Int) {
        holder.bindData(qualitiesList[position], onRemoveChosenQuality)
    }

    override fun getItemCount(): Int {
        return qualitiesList.size
    }

    class ChosenQualitiesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = ItemChosenListBinding.bind(itemView)

        fun bindData(quality: Quality, onRemoveChosenQuality: OnRemoveChosenQualityListener) {
            binding.name.text = quality.name
            binding.cancel.setOnClickListener {
                onRemoveChosenQuality.onRemoveChosenQuality(quality)
            }
        }
    }
}