package com.bydhiva.dismaps.ui.setting

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bydhiva.dismaps.databinding.FragmentSettingDialogBinding
import com.bydhiva.dismaps.worker.WaterLevelWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SettingDialogFragment : DialogFragment() {
    private var _binding: FragmentSettingDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveThemeSetting(isChecked)
        }
        binding.switchAlert.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveAlertSetting(isChecked)
        }
        viewModel.settings.observe(viewLifecycleOwner) {
            binding.switchDarkMode.isChecked = it.isDarkModeActive
            if (it.isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            binding.switchAlert.isChecked = it.isAlertActive
            val workManager = WorkManager.getInstance(requireContext())
            val waterLevelRequest = PeriodicWorkRequestBuilder<WaterLevelWorker>(1, TimeUnit.HOURS).build()
            if (it.isAlertActive) {
                workManager.enqueueUniquePeriodicWork(
                    WaterLevelWorker::class.java.name,
                    ExistingPeriodicWorkPolicy.KEEP,
                    waterLevelRequest
                )
            } else {
                workManager.cancelWorkById(waterLevelRequest.id)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}