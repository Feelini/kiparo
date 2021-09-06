package com.soldatov.taxi.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.soldatov.data.utils.Result
import com.soldatov.domain.models.DomainTaxiInfo
import com.soldatov.taxi.R
import com.soldatov.taxi.presentation.utils.ViewModelFactory
import com.soldatov.taxi.presentation.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var taxiInfo: List<DomainTaxiInfo>
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, ViewModelFactory())
            .get(MainActivityViewModel::class.java)

        mapView = findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)

        setupObservers()
        replaceTaxiListFragment()
    }

    private fun setupObservers() {
        viewModel.taxiInfo.observe(this, {
            when (it){
                is Result.Success -> {
                    taxiInfo = it.data
                    mapView.getMapAsync(this)
                }
                is Result.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Result.Loading -> {
                    //TODO preloader
                }
            }
        })
    }

    private fun replaceTaxiListFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.bottom_sheet, TaxiListFragment())
            .commit()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(p0: GoogleMap) {
        Log.d("TAG", "map")
        val bounds = LatLngBounds.Builder()
        taxiInfo.forEach{
            val position = LatLng(it.lat, it.lon)
            p0.addMarker(MarkerOptions().position(position))
            bounds.include(position)
        }
        p0.uiSettings.isZoomControlsEnabled = true
        p0.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 300))
    }
}