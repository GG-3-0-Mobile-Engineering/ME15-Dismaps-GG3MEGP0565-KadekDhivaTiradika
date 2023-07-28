package com.bydhiva.dismaps.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.base.BaseApplication
import com.bydhiva.dismaps.base.MainContainer
import com.bydhiva.dismaps.base.viewModelBuilder
import com.bydhiva.dismaps.databinding.ActivityMainBinding
import com.bydhiva.dismaps.ui.error.ErrorPlaceholderFragment
import com.bydhiva.dismaps.ui.list.DisasterListFragment
import com.bydhiva.dismaps.ui.maps.MapsFragment
import com.bydhiva.dismaps.ui.searchbar.SearchbarFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.search.SearchView
import com.bydhiva.dismaps.ui.main.MainViewModel.MainUIEvent
import com.bydhiva.dismaps.utils.removeIfExist

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModelBuilder<MainViewModel>()
    private var eventState: MainUIEvent = MainUIEvent.SuccessEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as BaseApplication).createMainContainer()
        initBottomSheet()
        initFragment()

        viewModel.mainUIEvent.observe(this) {
            when (it) {
                is MainUIEvent.ErrorEvent -> {
                    addErrorFragment()
                    setBottomSheetState(BottomSheetBehavior.STATE_COLLAPSED, true)
                }
                is MainUIEvent.EmptyEvent -> {
                    addErrorFragment()
                    setBottomSheetState(BottomSheetBehavior.STATE_COLLAPSED, true)
                }
                is MainUIEvent.SuccessEvent -> {
                    removePlaceHolderFragment()
                    setBottomSheetState(BottomSheetBehavior.STATE_HALF_EXPANDED)
                }
            }
            eventState = it
        }
        viewModel.searchViewState.observe(this) {
            when(it) {
                SearchView.TransitionState.SHOWING ->
                    setBottomSheetState(BottomSheetBehavior.STATE_COLLAPSED, true)
                SearchView.TransitionState.HIDDEN ->
                    if (eventState is MainUIEvent.SuccessEvent) setBottomSheetState(BottomSheetBehavior.STATE_HALF_EXPANDED)
                else -> {}
            }
        }
    }

    private fun initBottomSheet() {
        setBottomSheetState(BottomSheetBehavior.STATE_HALF_EXPANDED)
    }

    private fun initFragment() = supportFragmentManager.commit {
        replace<SearchbarFragment>(R.id.header_container)
        replace<MapsFragment>(R.id.main_container)
        replace<DisasterListFragment>(R.id.bottom_sheet_container)
        setReorderingAllowed(true)
    }

    private fun addErrorFragment() {
        if (eventState !is MainUIEvent.SuccessEvent) return
        supportFragmentManager.commit {
            add<ErrorPlaceholderFragment>(R.id.main_container)
            setReorderingAllowed(true)
        }
    }

    private fun removePlaceHolderFragment() = supportFragmentManager.removeIfExist<ErrorPlaceholderFragment>()

    private fun setBottomSheetState(newState: Int, isLocked: Boolean = false) {
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            state = newState
            isDraggable = !isLocked
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as BaseApplication).destroyMainContainer()
    }

}