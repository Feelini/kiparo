package com.soldatov.taxi.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.soldatov.data.utils.Result
import com.soldatov.domain.models.DomainTaxiInfo
import com.soldatov.taxi.R
import com.soldatov.taxi.presentation.utils.ViewModelFactory
import com.soldatov.taxi.presentation.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity(), OnMapReadyCallback, TaxiListAdapter.OnTaxiClickListener {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var mapView: MapView
    private lateinit var zoomInBtn: ImageButton
    private lateinit var zoomOutBtn: ImageButton
    private lateinit var preloader: ProgressBar
    private var map: GoogleMap? = null
    private var dataIsShow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, ViewModelFactory())
            .get(MainActivityViewModel::class.java)

        setupUI()

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        setupObservers()
        replaceTaxiListFragment()
    }

    private fun setupUI(){
        mapView = findViewById(R.id.map)
        zoomInBtn = findViewById(R.id.zoom_in)
        zoomOutBtn = findViewById(R.id.zoom_out)
        preloader = findViewById(R.id.preloader)
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet))

        zoomInBtn.setOnClickListener {
            map?.animateCamera(CameraUpdateFactory.zoomIn())
        }
        zoomOutBtn.setOnClickListener {
            map?.animateCamera(CameraUpdateFactory.zoomOut())
        }
    }

    private fun setupObservers() {
        viewModel.taxiInfo.observe(this, {
            when (it){
                is Result.Success -> {
                    if (map != null) {
                        preloader.visibility = INVISIBLE
                        showTaxiInfoOnMap(it.data)
                    }
                }
                is Result.Error -> {
                    preloader.visibility = INVISIBLE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Result.Loading -> {
                    preloader.visibility = VISIBLE
                }
            }
        })
    }

    private fun showTaxiInfoOnMap(taxiInfoList: List<DomainTaxiInfo>){
        val bounds = LatLngBounds.Builder()
        taxiInfoList.forEach{
            val position = LatLng(it.lat, it.lon)
            map?.addMarker(MarkerOptions().position(position))
            bounds.include(position)
        }
        map?.uiSettings?.isZoomControlsEnabled = false
        map?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 300))
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
        map = p0
        if (!dataIsShow){
            preloader.visibility = INVISIBLE
            val taxiInfo = viewModel.getTaxiInfo()
            if (taxiInfo != null){
                showTaxiInfoOnMap(taxiInfo)
            }
        }
    }

    override fun onTaxiClick(latLng: LatLng) {
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        map?.clear()
        map?.addMarker(MarkerOptions().position(latLng))
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}