package com.soldatov.covid.presentation.map

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.soldatov.covid.R
import com.soldatov.covid.domain.models.DomainCovidInfo
import com.soldatov.covid.presentation.viewmodel.MainActivityViewModel
import com.soldatov.covid.utils.Result
import com.soldatov.covid.utils.ViewModelFactory

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var preloader: ProgressBar
    private var map: GoogleMap? = null
    private var dataIsShow = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(activity as ViewModelStoreOwner, ViewModelFactory())
                .get(MainActivityViewModel::class.java)

        setupObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        preloader = view.findViewById(R.id.preloader)
    }

    private fun setupObservers() {
        viewModel.covidInfo.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    if (map != null){
                        preloader.visibility = View.INVISIBLE
                        showCovidInfoOnMap(it.data)
                        dataIsShow = true
                    }
                }
                is Result.Error -> {
                    preloader.visibility = View.INVISIBLE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                Result.Loading -> {
                    preloader.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showCovidInfoOnMap(domainCovidInfo: DomainCovidInfo){
        domainCovidInfo.countryInfoList.forEach {
            val latLng = stringToLatLng(it.lat, it.long)
            val radius = it.confirmed.toDouble().div(100)
            latLng?.let {
                map?.addCircle(
                    CircleOptions().center(latLng).radius(radius)
                        .fillColor(ContextCompat.getColor(requireContext(), R.color.red))
                        .strokeColor(Color.RED).strokeWidth(5f)
                )
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        preloader.visibility = View.INVISIBLE
        map = p0
        if (!dataIsShow){
            val covidInfo = viewModel.getCovidInfo()
            if (covidInfo != null) {
                showCovidInfoOnMap(covidInfo)
            }
        }
    }

    private fun stringToLatLng(lat: String, lng: String): LatLng? {
        return try {
            val latitude = lat.toDouble()
            val longitude = lng.toDouble()
            LatLng(latitude, longitude)
        } catch (e: Exception){
            null
        }
    }
}