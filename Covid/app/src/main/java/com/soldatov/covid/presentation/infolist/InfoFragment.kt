package com.soldatov.covid.presentation.infolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.covid.databinding.FragmentInfoBinding
import com.soldatov.covid.domain.models.DomainCountryInfo
import com.soldatov.covid.domain.models.DomainCovidInfo
import com.soldatov.covid.presentation.utils.dateFormat
import com.soldatov.covid.presentation.viewmodel.MainActivityViewModel
import com.soldatov.covid.utils.Result
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val infoListAdapter = InfoListAdapter()
    private val viewModel by sharedViewModel<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupObservers()

        binding.infoList.adapter = infoListAdapter
        binding.infoList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun setupObservers() {
        viewModel.covidInfo.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    binding.contentLayout.visibility = View.VISIBLE
                    binding.preloader.visibility = View.INVISIBLE
                    setupUI(it.data)
                }
                is Result.Error -> {
                    binding.preloader.visibility = View.INVISIBLE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                Result.Loading -> {
                    binding.contentLayout.visibility = View.INVISIBLE
                    binding.preloader.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupUI(covidInfo: DomainCovidInfo) {
        binding.lastUpdate.text = dateFormat(covidInfo.lastCheckTime)
        binding.confirmedNumber.text = covidInfo.totalConfirmed.toString()
        binding.totalDeathNumber.text = covidInfo.totalDeaths.toString()
        binding.recoveredNumber.text = covidInfo.totalRecovered.toString()
        showInfoList(covidInfo.countryInfoList)
    }

    private fun showInfoList(infoList: List<DomainCountryInfo>) {
        infoListAdapter.setCovidCountryInfo(infoList)
    }
}