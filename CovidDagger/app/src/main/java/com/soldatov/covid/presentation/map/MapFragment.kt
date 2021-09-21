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
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.soldatov.covid.R
import com.soldatov.covid.databinding.FragmentMapBinding
import com.soldatov.covid.domain.models.DomainCovidInfo
import com.soldatov.covid.presentation.viewmodel.MainActivityViewModel
import com.soldatov.covid.utils.Result
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var preloader: ProgressBar
    private var map: GoogleMap? = null
    private var dataIsShow = false
    private val viewModel by sharedViewModel<MainActivityViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                    binding.preloader.visibility = View.INVISIBLE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                Result.Loading -> {
                    binding.preloader.visibility = View.VISIBLE
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