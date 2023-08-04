package com.bydhiva.dismaps.ui.maps

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.base.activityViewModelBuilder
import com.bydhiva.dismaps.ui.main.MainViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private var googleMap: GoogleMap? = null
    private val latLngBounds by lazy { LatLngBounds.builder() }
    private val viewModel by activityViewModelBuilder<MainViewModel>()
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.maps_night_style)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        initObserver()
    }

    private fun initObserver() {
        viewModel.listDisaster.observe(viewLifecycleOwner) {disasters ->
            googleMap?.clear()
            disasters.forEach {
                googleMap?.addMarker(MarkerOptions().position(it.latLng))
                latLngBounds.include(it.latLng)
            }
            if (disasters.isNotEmpty()) {
                googleMap?.moveCamera(CameraUpdateFactory.newLatLng(latLngBounds.build().center))
            }
        }
    }
}