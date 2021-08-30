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
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.soldatov.covid.R
import com.soldatov.covid.domain.models.CovidInfo
import com.soldatov.covid.presentation.viewmodel.MainActivityViewModel
import com.soldatov.covid.utils.Result
import com.soldatov.covid.utils.ViewModelFactory

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var preloader: ProgressBar
    private lateinit var covidInfo: CovidInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, ViewModelFactory())
                .get(MainActivityViewModel::class.java)
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
        preloader = view.findViewById(R.id.preloader)

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.covidInfo.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    covidInfo = it.data
                    mapFragment.getMapAsync(this)
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

    override fun onMapReady(p0: GoogleMap) {
        preloader.visibility = View.INVISIBLE
        covidInfo.china.data.forEach {
            val latLng = stringToLatLng(it.lat, it.long)
            val radius = it.confirmed.toDouble().div(100)
            latLng?.let {
                p0.addCircle(
                    CircleOptions().center(latLng).radius(radius)
                        .fillColor(ContextCompat.getColor(requireContext(), R.color.red))
                        .strokeColor(Color.RED).strokeWidth(5f)
                )
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