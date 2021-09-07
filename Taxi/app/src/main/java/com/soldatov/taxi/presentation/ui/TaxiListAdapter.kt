package com.soldatov.taxi.presentation.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.soldatov.domain.models.DomainTaxiInfo
import com.soldatov.taxi.R

class TaxiListAdapter(private val onTaxiClickListener: OnTaxiClickListener) : RecyclerView.Adapter<TaxiListAdapter.TaxiListViewHolder>() {

    private var taxiList: List<DomainTaxiInfo> = ArrayList()

    interface OnTaxiClickListener{
        fun onTaxiClick(latLng: LatLng)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTaxiList(newTaxiList: List<DomainTaxiInfo>) {
        taxiList = newTaxiList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_taxi_list, parent, false)
        return TaxiListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaxiListViewHolder, position: Int) {
        holder.bindData(taxiList[position], onTaxiClickListener)
    }

    override fun getItemCount(): Int {
        return taxiList.size
    }

    class TaxiListViewHolder(
        itemView: View,
        private val taxiName: TextView = itemView.findViewById(R.id.taxi_name)
    ) : RecyclerView.ViewHolder(itemView) {

        fun bindData(taxiInfo: DomainTaxiInfo, onTaxiClickListener: OnTaxiClickListener) {
            taxiName.text = "Taxi № ${taxiInfo.id}"
            itemView.setOnClickListener { onTaxiClickListener.onTaxiClick(LatLng(taxiInfo.lat, taxiInfo.lon)) }
        }
    }
}