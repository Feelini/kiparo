package com.soldatov.covid.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soldatov.covid.R
import com.soldatov.covid.data.api.ApiHelper
import com.soldatov.covid.data.api.NetworkService
import com.soldatov.covid.domain.models.CovidInfo
import com.soldatov.covid.utils.Status
import com.soldatov.covid.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var preloader: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNavView)
        preloader = findViewById(R.id.preloader)
        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(ApiHelper(NetworkService.apiService)))
                .get(MainActivityViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getCovidInfo().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        preloader.visibility = View.INVISIBLE
                        bottomNavigationView.visibility = View.VISIBLE
                        resource.data?.let { covidInfo -> setupUI(covidInfo) }
                    }
                    Status.ERROR -> {
                        preloader.visibility = View.INVISIBLE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        bottomNavigationView.visibility = View.INVISIBLE
                        preloader.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setupUI(covidInfo: CovidInfo) {
        addMapFragment(covidInfo)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.map -> {
                    addMapFragment(covidInfo)
                    true
                }
                R.id.info -> {
                    addInfoFragment(covidInfo)
                    true
                }
                else -> false
            }
        }
    }

    private fun addInfoFragment(covidInfo: CovidInfo) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, InfoFragment(covidInfo))
            .commit()
    }

    private fun addMapFragment(covidInfo: CovidInfo) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MapFragment(covidInfo))
            .commit()
    }
}
