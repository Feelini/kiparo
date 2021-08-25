package com.soldatov.covid.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.soldatov.covid.R
import com.soldatov.covid.data.api.ApiHelper
import com.soldatov.covid.data.api.NetworkService
import com.soldatov.covid.domain.models.CovidInfo
import com.soldatov.covid.utils.Status
import com.soldatov.covid.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
//        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(ApiHelper(NetworkService.apiService)))
                .get(MainActivityViewModel::class.java)
    }

    private fun setupObservers(){
        viewModel.getCovidInfo().observe(this, Observer {
            it?.let { resource ->
                when (resource.status){
                    Status.SUCCESS -> {
                        resource.data?.let { covidInfo -> show(covidInfo) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun show(covidInfo: CovidInfo){
        println(covidInfo)
    }
}