package com.soldatov.covid.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.soldatov.covid.R
import com.soldatov.covid.domain.models.CovidInfo

class MapFragment(private val covidInfo: CovidInfo) : Fragment(), OnMapReadyCallback {

    private lateinit var mapFragment: SupportMapFragment

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
    }

    override fun onMapReady(p0: GoogleMap) {
        covidInfo.china.data.forEach {
            val latLng = stringToLatLng(it.lat, it.long)
            val radius = it.confirmed?.toDouble()?.div(100)
            if (latLng != null) {
                p0.addCircle(
                    CircleOptions().center(latLng).radius(radius!!)
                        .fillColor(ContextCompat.getColor(requireContext(), R.color.red))
                        .strokeColor(Color.RED).strokeWidth(5f)
                )
            }
        }
    }

    private fun stringToLatLng(lat: String?, lng: String?): LatLng? {
        var latitude: Double? = null
        var longitude: Double? = null
        lat?.let {
            latitude = lat.toDouble()
        }
        lng?.let {
            longitude = lng.toDouble()
        }
        return if (latitude != null && longitude != null) {
            LatLng(latitude!!, longitude!!)
        } else {
            null
        }
    }
}