package com.bydhiva.dismaps.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.databinding.ActivityMainBinding
import com.bydhiva.dismaps.ui.additional.AdditionalInfoFragment
import com.bydhiva.dismaps.ui.detail.DisasterDetailFragment
import com.bydhiva.dismaps.ui.detail.HeaderDisasterDetailFragment
import com.bydhiva.dismaps.ui.error.ErrorPlaceholderFragment
import com.bydhiva.dismaps.ui.list.DisasterListFragment
import com.bydhiva.dismaps.ui.main.MainViewModel.MainUIEvent
import com.bydhiva.dismaps.ui.maps.MapsFragment
import com.bydhiva.dismaps.ui.searchbar.SearchbarFragment
import com.bydhiva.dismaps.utils.removeIfExist
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private var eventState: MainUIEvent = MainUIEvent.SuccessEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                    removeErrorFragment()
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
        viewModel.selectedDisaster.observe(this) {
            if (it == null) {
                removeDetailDisasterFragment()
            } else {
                addDetailDisasterFragment()
            }
            setBottomSheetState(BottomSheetBehavior.STATE_HALF_EXPANDED)
        }
    }

    private fun initBottomSheet() {
        setBottomSheetState(BottomSheetBehavior.STATE_HALF_EXPANDED)
        setTranslationYBottomSheetHeader(0.4F)
        BottomSheetBehavior.from(binding.bottomSheet).addBottomSheetCallback(
            object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) = Unit
                override fun onSlide(bottomSheet: View, slideOffset: Float) =
                    setTranslationYBottomSheetHeader(slideOffset)
            }
        )
    }

    private fun initFragment() = supportFragmentManager.commit {
        replace<SearchbarFragment>(R.id.header_container)
        replace<MapsFragment>(R.id.main_container)
        replace<DisasterListFragment>(R.id.bottom_sheet_container)
        replace<AdditionalInfoFragment>(R.id.bs_header_container)
        setReorderingAllowed(true)
    }

    private fun addErrorFragment() {
        if (eventState !is MainUIEvent.SuccessEvent) return
        supportFragmentManager.commit {
            add<ErrorPlaceholderFragment>(R.id.main_container)
            setReorderingAllowed(true)
        }
    }

    private fun removeErrorFragment() = supportFragmentManager.removeIfExist<ErrorPlaceholderFragment>()

    private fun addDetailDisasterFragment() {
        supportFragmentManager.commit {
            add<DisasterDetailFragment>(R.id.bottom_sheet_container)
            add<HeaderDisasterDetailFragment>(R.id.header_container)
            setReorderingAllowed(true)
        }
    }

    private fun removeDetailDisasterFragment() {
        supportFragmentManager.removeIfExist<DisasterDetailFragment>()
        supportFragmentManager.removeIfExist<HeaderDisasterDetailFragment>()
    }

    private fun setBottomSheetState(newState: Int, isLocked: Boolean = false) {
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            state = newState
            isDraggable = !isLocked
        }
    }

    private fun setTranslationYBottomSheetHeader(slideOffset: Float) {
        val height = resources.displayMetrics.heightPixels-100
        val translationY = -(slideOffset*height)
        val extraTranslationY: Float = if (slideOffset<0.4) {
            200-(200*(slideOffset*2.5)).toFloat()
        } else {
            300-(300*((1-slideOffset)*1.66)).toFloat()
        }
        binding.cvBsHeader.translationY = translationY+ extraTranslationY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}