package com.bydhiva.dismaps.ui.maps

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.base.activityViewModelBuilder
import com.bydhiva.dismaps.domain.model.Disaster
import com.bydhiva.dismaps.domain.model.DisasterType
import com.bydhiva.dismaps.ui.main.MainViewModel
import com.bydhiva.dismaps.utils.CombinedLiveData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment(), OnMapReadyCallback {
    private var googleMap: GoogleMap? = null
    private val latLngBounds by lazy { LatLngBounds.builder() }
    private val viewModel by activityViewModelBuilder<MainViewModel>()
    private var floodMarker: Bitmap? = null
    private var earthquakeMarker: Bitmap? = null
    private var fireMarker: Bitmap? = null
    private var hazeMarker: Bitmap? = null
    private var windMarker: Bitmap? = null
    private var volcanoMarker: Bitmap? = null

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
        mapFragment?.getMapAsync(this)

        initObserver()
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            googleMap?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.maps_night_style)
            )
        }
    }

    private fun initObserver() {
        CombinedLiveData(
            source1 = viewModel.listDisaster,
            source2 = viewModel.selectedDisaster,
            combine = { data1, data2 -> Pair(data1, data2) }
        ).observe(viewLifecycleOwner) {
            val selected = it.second
            val list = it.first ?: listOf()
            if (selected != null) {
                toSelectedLocation(selected.latLng)
            } else {
                centerMaps(list)
            }
        }
    }

    private fun centerMaps(disasters: List<Disaster>) {
        googleMap?.clear()
        disasters.forEach {
            googleMap?.addMarker(
                MarkerOptions()
                    .position(it.latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmap(it.disasterType)))
            )
            latLngBounds.include(it.latLng)
        }
        if (disasters.isNotEmpty()) {
            googleMap?.let {
                val paddingBottom = resources.displayMetrics.heightPixels * 0.35
                it.setPadding(0, 0, 0, paddingBottom.toInt())
                it.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngBounds.build().center, 3.5F))
            }
        }
    }

    private fun toSelectedLocation(location: LatLng) {
        googleMap?.let {
            val paddingBottom = resources.displayMetrics.heightPixels * 0.35
            it.setPadding(0, 0, 0, paddingBottom.toInt())
            it.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.5F))
        }
    }
    private fun getMarkerBitmap(type: DisasterType): Bitmap {
        when(type) {
            DisasterType.FLOOD -> {
                floodMarker?.let { return it }
                val bitmap = getBitmapFromDrawable(R.drawable.ic_flood_marker)
                floodMarker = bitmap
                return bitmap
            }
            DisasterType.EARTHQUAKE -> {
                earthquakeMarker?.let { return it }
                val bitmap = getBitmapFromDrawable(R.drawable.ic_earthquake_marker)
                earthquakeMarker = bitmap
                return bitmap
            }
            DisasterType.FIRE -> {
                fireMarker?.let { return it }
                val bitmap = getBitmapFromDrawable(R.drawable.ic_fire_marker)
                fireMarker = bitmap
                return bitmap
            }
            DisasterType.HAZE -> {
                hazeMarker?.let { return it }
                val bitmap = getBitmapFromDrawable(R.drawable.ic_haze_marker)
                hazeMarker = bitmap
                return bitmap
            }
            DisasterType.WIND -> {
                windMarker?.let { return it }
                val bitmap = getBitmapFromDrawable(R.drawable.ic_wind_marker)
                windMarker = bitmap
                return bitmap
            }
            DisasterType.VOLCANO -> {
                volcanoMarker?.let { return it }
                val bitmap = getBitmapFromDrawable(R.drawable.ic_volcano_marker)
                volcanoMarker = bitmap
                return bitmap
            }
            DisasterType.DEFAULT -> {
                floodMarker?.let { return it }
                val bitmap = getBitmapFromDrawable(R.drawable.ic_flood_marker)
                floodMarker = bitmap
                return bitmap
            }
        }
    }

    private fun getBitmapFromDrawable(id: Int): Bitmap {
        val bitmap = BitmapFactory.decodeResource(resources, id)
        return Bitmap.createScaledBitmap(bitmap, 170, 170, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        googleMap = null
        floodMarker = null
        earthquakeMarker = null
        fireMarker = null
        hazeMarker = null
        windMarker = null
        volcanoMarker = null
    }
}