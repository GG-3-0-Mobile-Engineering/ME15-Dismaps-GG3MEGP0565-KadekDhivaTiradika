package com.bydhiva.dismaps.ui.searchbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.common.adapter.ListSuggestionAdapter
import com.bydhiva.dismaps.databinding.FragmentSearchbarBinding
import com.bydhiva.dismaps.domain.model.DisasterType
import com.bydhiva.dismaps.domain.model.Province
import com.bydhiva.dismaps.ui.main.MainViewModel
import com.bydhiva.dismaps.ui.setting.SettingDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class SearchbarFragment : Fragment() {

    private var _binding: FragmentSearchbarBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<MainViewModel>()
    private val listSuggestionAdapter by lazy { ListSuggestionAdapter() }
    private val datePicker by lazy {
        MaterialDatePicker.Builder
            .dateRangePicker()
            .setTitleText(getString(R.string.set_filter))
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchbarBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchView()
        initSuggestionRecyclerView()
        initChips()
        initDatePicker()

        viewModel.reportsFilter.observe(viewLifecycleOwner) {
            viewModel.getReports()
            binding.chipClearFilter.visibility =
                if (it.startEndDate == null) View.GONE else View.VISIBLE
            setFilterChipIsChecked(it.startEndDate)
        }
        viewModel.searchText.observe(viewLifecycleOwner) { text ->
            val suggestions = getProvinceList().filter {
                it.name.contains(text, true)
            }
            listSuggestionAdapter.submitList(suggestions)
        }
    }

    private fun initSearchView() = with(binding) {
        searchBar.inflateMenu(R.menu.search_view_menu)
        searchBar.setOnMenuItemClickListener {
            SettingDialogFragment().show(requireActivity().supportFragmentManager, null)
            return@setOnMenuItemClickListener false
        }
        searchView.addTransitionListener { _, _, newState ->
            viewModel.setSearchViewState(newState)
        }
        searchView.editText.addTextChangedListener {
            viewModel.setSearchText(it.toString())
        }
        searchView.editText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) onActionSearch(v.text.toString())
            return@setOnEditorActionListener true
        }
    }

    private fun initSuggestionRecyclerView() = with(binding) {
        rvSuggestions.adapter = listSuggestionAdapter
        rvSuggestions.layoutManager = LinearLayoutManager(requireContext())
        listSuggestionAdapter.setOnItemClickCallback(object : ListSuggestionAdapter.OnItemClickCallback{
            override fun onClick(suggestion: Province) {
                viewModel.setSearchText(suggestion.name)
                onActionSearch(suggestion.name)
            }
        })
        listSuggestionAdapter.submitList(getProvinceList())
    }

    private fun initChips() = with(binding) {
        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isEmpty()) {
                viewModel.setDisasterTypeFilter(null)
                return@setOnCheckedStateChangeListener
            }
            when(checkedIds.first()) {
                R.id.chip_disaster_flood -> viewModel.setDisasterTypeFilter(DisasterType.FLOOD)
                R.id.chip_disaster_earthquake -> viewModel.setDisasterTypeFilter(DisasterType.EARTHQUAKE)
                R.id.chip_disaster_fire -> viewModel.setDisasterTypeFilter(DisasterType.FIRE)
                R.id.chip_disaster_haze -> viewModel.setDisasterTypeFilter(DisasterType.HAZE)
                R.id.chip_disaster_wind -> viewModel.setDisasterTypeFilter(DisasterType.WIND)
                R.id.chip_disaster_volcano -> viewModel.setDisasterTypeFilter(DisasterType.VOLCANO)
            }
        }
        chipClearFilter.setOnClickListener {
            viewModel.setDateFilter(null)
        }
    }

    private fun initDatePicker() {
        binding.chipFilter.setOnClickListener {
            if (datePicker.isAdded) return@setOnClickListener
            datePicker.show(requireActivity().supportFragmentManager, null)
        }
        datePicker.addOnPositiveButtonClickListener {
            val (start, end) = listOf(Calendar.getInstance(), Calendar.getInstance())
            start.timeInMillis = it.first
            end.timeInMillis = it.second
            viewModel.setDateFilter(Pair(start.time, end.time))
        }
    }

    private fun setFilterChipIsChecked(starEndDate: Pair<Date, Date>?) = with(binding) {
        chipFilter.isCheckable = true
        chipFilter.isChecked = starEndDate != null
        chipFilter.isCheckable = false
    }

    private fun onActionSearch(text: String) = with(binding) {
        searchView.hide()
        searchBar.text = text
        viewModel.getReports()
    }

    private fun getProvinceList(): List<Province> {
        val provincesCode = resources.getStringArray(R.array.province_code)
        return resources.getStringArray(R.array.province_name).mapIndexed { index, s ->
            Province(s, provincesCode[index])
        }.toList().sortedBy { it.name }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}