package com.bydhiva.dismaps.ui.detail

import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.base.activityViewModelBuilder
import com.bydhiva.dismaps.databinding.FragmentDisasterDetailBinding
import com.bydhiva.dismaps.databinding.FragmentHeaderDisasterDetailBinding
import com.bydhiva.dismaps.domain.model.Disaster
import com.bydhiva.dismaps.ui.main.MainViewModel
import com.bydhiva.dismaps.utils.toShortText
import java.util.Locale

class HeaderDisasterDetailFragment : Fragment() {
    private var _binding: FragmentHeaderDisasterDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModelBuilder<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeaderDisasterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedDisaster.observe(viewLifecycleOwner) { disaster ->
            disaster?.let { initView(it) }
        }
    }

    private fun initView(disaster: Disaster) = with(binding) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        var address = "-"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(
                disaster.latLng.latitude,
                disaster.latLng.longitude,
                1,
            ) {
                val res = it.first()
                address = "${res.locality}, ${res.subAdminArea}, ${res.adminArea}, ${res.countryName}"
                tvAddress.text = address
            }
        } else {
            @Suppress("DEPRECATION")
            val res = geocoder.getFromLocation(
                disaster.latLng.latitude,
                disaster.latLng.longitude,
                1,
            )?.first()
            res?.let {
                address = "${it.locality}, ${it.subAdminArea}, ${it.adminArea}, ${it.countryName}"
            }
        }

        ibBack.setOnClickListener {
            viewModel.setSelectedDisaster(null)
        }
        tvDate.text = disaster.createdAt?.toShortText()
        tvAddress.text = address

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}