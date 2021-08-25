package com.soldatov.covid.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.covid.R
import com.soldatov.covid.domain.models.CovidCountryInfo

class InfoListAdapter(private val infoList: List<CovidCountryInfo>) :
    RecyclerView.Adapter<InfoListAdapter.InfoListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_info_list, parent, false)
        return InfoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfoListViewHolder, position: Int) {
        holder.bindData(infoList[position])
    }

    override fun getItemCount(): Int {
        return infoList.size
    }

    class InfoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var infoNumber: TextView
        private lateinit var infoText: TextView

        fun bindData(covidCountryInfo: CovidCountryInfo){
            infoNumber = itemView.findViewById(R.id.item_number)
            infoText = itemView.findViewById(R.id.item_text)
            infoNumber.text = covidCountryInfo.confirmed
            infoText.text = covidCountryInfo.country
        }
    }
}