package com.soldatov.covid.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soldatov.covid.R
import com.soldatov.covid.data.api.ApiHelper
import com.soldatov.covid.data.api.NetworkService
import com.soldatov.covid.domain.models.CovidInfo
import com.soldatov.covid.utils.Status
import com.soldatov.covid.utils.ViewModelFactory

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var bottomNavigationView: BottomNavigationView
    private val mapFragment = SupportMapFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNavView)
        bottomNavigationView.selectedItemId = R.id.map
        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(ApiHelper(NetworkService.apiService)))
                .get(MainActivityViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getCovidInfo().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { covidInfo -> setupUI(covidInfo) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun setupUI(covidInfo: CovidInfo) {
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.map -> {
                    addMapFragment()
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

    private fun addMapFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, mapFragment)
            .commit()
    }

    override fun onMapReady(p0: GoogleMap) {
        p0.addMarker(MarkerOptions().position(LatLng(46.227600000000002, 2.2136999999999998)).title("Title"))
    }
}
