package com.soldatov.vkino.presentation.ui.filter.country

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Country
import com.soldatov.vkino.databinding.ItemFilterListBinding
import com.soldatov.vkino.databinding.ItemProgressBarBinding

class CountriesListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var countriesList: List<Country> = listOf()
    private var chosenCountries: ArrayList<Country> = arrayListOf()
    private val VIEW_ITEM = 1
    private val VIEW_PROG = 0
    private var isProgress = true

    @SuppressLint("NotifyDataSetChanged")
    fun setSearchCountries(countryList: List<Country>, newChosenCountries: List<Country>) {
        countriesList = countryList
        chosenCountries = newChosenCountries as ArrayList<Country>
        notifyDataSetChanged()
    }

    fun addProgress() {
        isProgress = true
    }

    fun removeProgress() {
        isProgress = false
    }

    fun getChosenCountries(): List<Country> {
        return chosenCountries.toList()
    }

    private val addChosenCountry: (Country) -> Unit = {
        chosenCountries.add(it)
    }

    private val removeChosenCountry: (Country) -> Unit = {
        chosenCountries.remove(it)
    }

    private val isCheckedCountry: (Country) -> Boolean = {
        chosenCountries.contains(it)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == countriesList.size) VIEW_PROG else VIEW_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        viewHolder = if (viewType == VIEW_ITEM) {
            val binding = ItemFilterListBinding.inflate(inflater, parent, false)
            SearchCountriesViewHolder(binding.root)
        } else {
            val binding = ItemProgressBarBinding.inflate(inflater, parent, false)
            ProgressBarViewHolder(binding.root)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchCountriesViewHolder) {
            holder.bindData(
                countriesList[position],
                isCheckedCountry,
                addChosenCountry,
                removeChosenCountry
            )
        } else {
            (holder as ProgressBarViewHolder).bindData()
        }
    }

    override fun getItemCount(): Int {
        return if (isProgress) {
            if (countriesList.isEmpty()) 0 else countriesList.size + 1
        } else {
            countriesList.size
        }
    }

    class SearchCountriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemFilterListBinding.bind(itemView)

        fun bindData(
            country: Country,
            isChecked: (Country) -> Boolean,
            addCountry: (Country) -> Unit,
            removeCountry: (Country) -> Unit
        ) {
            binding.filterTitle.text = country.name
            binding.checkbox.isChecked = isChecked(country)
            itemView.setOnClickListener {
                if (isChecked(country)) {
                    removeCountry(country)
                    binding.checkbox.isChecked = false
                } else {
                    addCountry(country)
                    binding.checkbox.isChecked = true
                }
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