package com.soldatov.covid.presentation.infolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.covid.R
import com.soldatov.covid.domain.models.DomainCountryInfo
import com.soldatov.covid.domain.models.DomainCovidInfo
import com.soldatov.covid.presentation.utils.dateFormat
import com.soldatov.covid.presentation.viewmodel.MainActivityViewModel
import com.soldatov.covid.utils.Result
import com.soldatov.covid.utils.ViewModelFactory

class InfoFragment : Fragment() {

    private lateinit var lastUpdate: TextView
    private lateinit var totalConfirmed: TextView
    private lateinit var totalDeath: TextView
    private lateinit var totalRecovered: TextView
    private lateinit var infoListView: RecyclerView
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var preloader: ProgressBar
    private lateinit var viewContainer: ConstraintLayout
    private val infoListAdapter = InfoListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(activity as ViewModelStoreOwner, ViewModelFactory())
                .get(MainActivityViewModel::class.java)

        setupObservers()

        infoListView.adapter = infoListAdapter
        infoListView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preloader = view.findViewById(R.id.preloader)
        lastUpdate = view.findViewById(R.id.last_update)
        totalConfirmed = view.findViewById(R.id.confirmed_number)
        totalDeath = view.findViewById(R.id.total_death_number)
        totalRecovered = view.findViewById(R.id.recovered_number)
        infoListView = view.findViewById(R.id.info_list)
        viewContainer = view.findViewById(R.id.content_layout)
    }

    private fun setupObservers() {
        viewModel.covidInfo.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    viewContainer.visibility = View.VISIBLE
                    preloader.visibility = View.INVISIBLE
                    setupUI(it.data)
                }
                is Result.Error -> {
                    preloader.visibility = View.INVISIBLE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                Result.Loading -> {
                    viewContainer.visibility = View.INVISIBLE
                    preloader.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupUI(covidInfo: DomainCovidInfo) {
        lastUpdate.text = dateFormat(covidInfo.lastCheckTimeMilli)
        totalConfirmed.text = covidInfo.totalConfirmed.toString()
        totalDeath.text = covidInfo.totalDeaths.toString()
        totalRecovered.text = covidInfo.totalRecovered.toString()
        showInfoList(covidInfo.countryInfoList)
    }

    private fun showInfoList(infoList: List<DomainCountryInfo>) {
        infoListAdapter.setCovidCountryInfo(infoList)
    }
}