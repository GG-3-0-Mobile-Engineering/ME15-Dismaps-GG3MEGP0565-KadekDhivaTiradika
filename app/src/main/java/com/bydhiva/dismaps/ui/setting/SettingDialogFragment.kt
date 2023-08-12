package com.bydhiva.dismaps.ui.setting

import android.Manifest
import android.app.ActionBar.LayoutParams
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bydhiva.dismaps.databinding.FragmentSettingDialogBinding
import com.bydhiva.dismaps.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingDialogFragment : DialogFragment() {
    private var _binding: FragmentSettingDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SettingViewModel>()
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        this::requestPermissionResult
    )

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
            binding.switchAlert.isChecked = it.isAlertActive

            if (it.isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            if (it.isAlertActive) {
                checkNotificationPermission()
            } else {
                viewModel.cancelWaterLevelWorker()
            }
        }
    }

    private fun checkNotificationPermission() {
         if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
             != PackageManager.PERMISSION_GRANTED) {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                 requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
             } else {
                 viewModel.saveAlertSetting(true)
                 viewModel.setWaterLevelWorker()
             }
         }
    }

    private fun requestPermissionResult(isGranted: Boolean) {
        if (!isGranted) {
            toast("Cannot send alert due to permission!")
            viewModel.saveAlertSetting(false)
        } else {
            viewModel.saveAlertSetting(true)
            viewModel.setWaterLevelWorker()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}