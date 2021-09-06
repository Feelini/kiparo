package com.soldatov.covid.presentation.infolist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.covid.R
import com.soldatov.covid.domain.models.CovidCountryInfo

class InfoListAdapter : RecyclerView.Adapter<InfoListAdapter.InfoListViewHolder>() {

    private var infoCountryList: List<CovidCountryInfo> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setCovidCountryInfo(infoList: List<CovidCountryInfo>) {
        infoCountryList = infoList
        notifyDataSetChanged()
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

    class InfoListViewHolder(
        itemView: View,
        private val infoNumber: TextView = itemView.findViewById(R.id.item_number),
        private val infoText: TextView = itemView.findViewById(R.id.item_text)
    ) : RecyclerView.ViewHolder(itemView) {

        fun bindData(covidCountryInfo: CovidCountryInfo) {
            infoNumber.text = covidCountryInfo.confirmed
            infoText.text = covidCountryInfo.country
        }
    }
}