package com.bydhiva.dismaps.ui.additional

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.base.activityViewModelBuilder
import com.bydhiva.dismaps.databinding.FragmentAdditionalInfoBinding
import com.bydhiva.dismaps.domain.model.Disaster
import com.bydhiva.dismaps.domain.model.ReportsFilter
import com.bydhiva.dismaps.ui.main.MainViewModel
import com.bydhiva.dismaps.utils.CombinedLiveData
import com.bydhiva.dismaps.utils.toShortText

class AdditionalInfoFragment : Fragment() {
    private var _binding: FragmentAdditionalInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModelBuilder<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdditionalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CombinedLiveData(
            source1 = viewModel.listDisaster,
            source2 = viewModel.reportsFilter,
            combine = { data1, data2 ->
                Pair(data1, data2)
            }
        ).observe(viewLifecycleOwner) {
            setMessage(it)
        }
    }

    private fun setMessage(data: Pair<List<Disaster>?, ReportsFilter?>) {
        val disasterCount = data.first?.size ?: 0
        val filter = data.second?.startEndDate?.toShortText()
        val time = if (filter == null) {
            getString(R.string.in_the_past_hour)
        } else {
            getString(R.string.between_date, filter)
        }

        binding.tvMessage.text = getString(R.string.disaster_message, disasterCount, time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}