package com.soldatov.covid.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.covid.R
import com.soldatov.covid.domain.models.CovidCountryInfo
import com.soldatov.covid.domain.models.CovidInfo
import java.text.SimpleDateFormat

class InfoFragment(private val covidInfo: CovidInfo) : Fragment() {

    private lateinit var lastUpdate: TextView
    private lateinit var totalConfirmed: TextView
    private lateinit var totalDeath: TextView
    private lateinit var totalRecovered: TextView
    private lateinit var infoListView: RecyclerView
    private lateinit var infoListAdapter: InfoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lastUpdate = view.findViewById(R.id.last_update)
        val simpleDateFormat = SimpleDateFormat("dd MMM, yyyy")
        val dateString = "last update " + simpleDateFormat.format(covidInfo.lastCheckTimeMilli)
        lastUpdate.text = dateString
        totalConfirmed = view.findViewById(R.id.confirmed_number)
        totalConfirmed.text = covidInfo.china.totalConfirmed.toString()
        totalDeath = view.findViewById(R.id.total_death_number)
        totalDeath.text = covidInfo.china.totalDeaths.toString()
        totalRecovered = view.findViewById(R.id.recovered_number)
        totalRecovered.text = covidInfo.china.totalRecovered.toString()
        infoListView = view.findViewById(R.id.info_list)
        showInfoList(covidInfo.china.data)
    }

    private fun showInfoList(infoList: List<CovidCountryInfo>){
        infoListAdapter = InfoListAdapter(infoList = infoList)
        infoListView.adapter = infoListAdapter
        infoListView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }
}