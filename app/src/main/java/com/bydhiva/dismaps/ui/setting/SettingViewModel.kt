package com.bydhiva.dismaps.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bydhiva.dismaps.domain.model.Setting
import com.bydhiva.dismaps.domain.model.SettingType
import com.bydhiva.dismaps.domain.usecase.setting.GetSettingsUseCase
import com.bydhiva.dismaps.domain.usecase.setting.SaveSettingUseCase
import com.bydhiva.dismaps.worker.WaterLevelWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingUseCase: SaveSettingUseCase,
    private val workManager: WorkManager
) : ViewModel() {
    private val _settings = MutableLiveData<Setting>()
    val settings get() = _settings
    private val waterLevelRequest by lazy {
        PeriodicWorkRequestBuilder<WaterLevelWorker>(1, TimeUnit.HOURS).build()
    }

    init {
        getSettings()
    }

    private fun getSettings() = viewModelScope.launch {
        getSettingsUseCase().collectLatest {
            _settings.postValue(it)
        }
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            saveSettingUseCase.saveBoolean(SettingType.DARK_MODE, isDarkModeActive)
        }
    }

    fun saveAlertSetting(isAlertActive: Boolean) {
        viewModelScope.launch {
            saveSettingUseCase.saveBoolean(SettingType.WATER_LEVEL_ALERT, isAlertActive)
        }
    }

    fun setWaterLevelWorker() {
        workManager.enqueueUniquePeriodicWork(
            WaterLevelWorker::class.java.name,
            ExistingPeriodicWorkPolicy.KEEP,
            waterLevelRequest
        )
    }

    fun cancelWaterLevelWorker() {
        workManager.cancelWorkById(waterLevelRequest.id)
    }

}