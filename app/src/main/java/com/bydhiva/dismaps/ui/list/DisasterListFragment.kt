package com.bydhiva.dismaps.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bydhiva.dismaps.base.activityViewModelBuilder
import com.bydhiva.dismaps.common.adapter.ListDisasterAdapter
import com.bydhiva.dismaps.databinding.FragmentDisasterListBinding
import com.bydhiva.dismaps.ui.main.MainViewModel

class DisasterListFragment : Fragment() {
    private val viewModel by activityViewModelBuilder<MainViewModel>()
    private var _binding: FragmentDisasterListBinding? = null
    private val binding get() = _binding!!
    private val listDisasterAdapter = ListDisasterAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDisasterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        viewModel.listDisaster.observe(viewLifecycleOwner) {
            listDisasterAdapter.submitList(it)
        }
        viewModel.isListLoading.observe(viewLifecycleOwner) {
            showShimmer(it)
        }
    }

    private fun initRecyclerView() {
        with(binding) {
            rvDisaster.layoutManager = LinearLayoutManager(requireContext())
            rvDisaster.adapter = listDisasterAdapter
        }
    }

    private fun showShimmer(show: Boolean) = with(binding) {
        shimmerContainer.showShimmer(show)
        shimmerContainer.visibility = if (show) View.VISIBLE else View.GONE
        rvDisaster.visibility = if (!show) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}