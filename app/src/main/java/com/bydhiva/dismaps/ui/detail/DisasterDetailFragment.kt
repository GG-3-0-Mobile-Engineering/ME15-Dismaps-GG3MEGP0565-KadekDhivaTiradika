package com.bydhiva.dismaps.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bydhiva.dismaps.base.activityViewModelBuilder
import com.bydhiva.dismaps.databinding.FragmentDisasterDetailBinding
import com.bydhiva.dismaps.domain.model.Disaster
import com.bydhiva.dismaps.ui.main.MainViewModel
import com.bydhiva.dismaps.utils.changeDrawable
import com.bydhiva.dismaps.utils.getDisasterIconId
import com.bydhiva.dismaps.utils.getStringId
import com.bydhiva.dismaps.utils.loadImage
import com.bydhiva.dismaps.utils.getColorId

class DisasterDetailFragment : Fragment() {

    private var _binding: FragmentDisasterDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModelBuilder<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDisasterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedDisaster.observe(viewLifecycleOwner) { disaster ->
            disaster?.let { initView(it) }
        }
    }

    private fun initView(disaster: Disaster) = with(binding) {
        tvTitle.text = disaster.title.ifBlank { getString(disaster.disasterType.getStringId()) }
        tvDesc.text = disaster.text
        tvDisasterType.text = getString(disaster.disasterType.getStringId())
        tvDisasterType.setTextColor(ContextCompat.getColor(requireContext(), disaster.disasterType.getColorId()))
        ivDisaster.loadImage(disaster.imageUrl, disaster.disasterType)
        ivIconDisaster.changeDrawable(disaster.disasterType.getDisasterIconId())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}