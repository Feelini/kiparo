package com.soldatov.covid.presentation.infolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.covid.R
import com.soldatov.covid.domain.models.CovidCountryInfo

class InfoListAdapter : RecyclerView.Adapter<InfoListAdapter.InfoListViewHolder>() {

    private var infoCountryList: List<CovidCountryInfo> = ArrayList()

    fun setCovidCountryInfo(infoList: List<CovidCountryInfo>) {
        infoCountryList = infoList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_info_list, parent, false)
        return InfoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfoListViewHolder, position: Int) {
        holder.bindData(infoCountryList[position])
    }

    override fun getItemCount(): Int {
        return infoCountryList.size
    }

    class InfoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var infoNumber: TextView
        private lateinit var infoText: TextView

        fun bindData(covidCountryInfo: CovidCountryInfo) {
            infoNumber = itemView.findViewById(R.id.item_number)
            infoText = itemView.findViewById(R.id.item_text)
            infoNumber.text = covidCountryInfo.confirmed
            infoText.text = covidCountryInfo.country
        }
    }
}