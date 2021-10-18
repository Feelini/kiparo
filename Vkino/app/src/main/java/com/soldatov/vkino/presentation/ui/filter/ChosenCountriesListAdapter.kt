package com.soldatov.vkino.presentation.ui.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Country
import com.soldatov.vkino.databinding.ItemChosenListBinding

class ChosenCountriesListAdapter: RecyclerView.Adapter<ChosenCountriesListAdapter.ChosenCountriesListViewHolder>() {

    private var countriesList: List<Country> = listOf()
    private lateinit var onRemoveChosenCountryListener: OnRemoveChosenCountryListener

    interface OnRemoveChosenCountryListener{
        fun onRemoveChosenCountry(country: Country)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setChosenCountries(
        newCountries: List<Country>,
        onRemoveChosenCountry: OnRemoveChosenCountryListener
    ){
        countriesList = newCountries
        onRemoveChosenCountryListener = onRemoveChosenCountry
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChosenCountriesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChosenListBinding.inflate(inflater, parent, false)
        return ChosenCountriesListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ChosenCountriesListViewHolder, position: Int) {
        holder.bindData(countriesList[position], onRemoveChosenCountryListener)
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

    class ChosenCountriesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = ItemChosenListBinding.bind(itemView)

        fun bindData(country: Country, onRemoveChosenCountry: OnRemoveChosenCountryListener){
            binding.name.text = country.name
            binding.cancel.setOnClickListener {
                onRemoveChosenCountry.onRemoveChosenCountry(country)
            }
        }
    }
}