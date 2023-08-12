package com.bydhiva.dismaps.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bydhiva.dismaps.domain.model.Setting
import com.bydhiva.dismaps.domain.model.SettingType
import com.bydhiva.dismaps.domain.usecase.setting.GetSettingsUseCase
import com.bydhiva.dismaps.domain.usecase.setting.SaveSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingUseCase: SaveSettingUseCase
) : ViewModel() {
    private val _settings = MutableLiveData<Setting>()
    val settings get() = _settings

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

}