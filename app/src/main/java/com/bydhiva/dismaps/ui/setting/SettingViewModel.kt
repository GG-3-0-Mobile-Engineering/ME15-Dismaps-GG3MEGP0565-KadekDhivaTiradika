package com.bydhiva.dismaps.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bydhiva.dismaps.domain.model.Setting
import com.bydhiva.dismaps.domain.usecase.setting.SettingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingUseCases: SettingUseCases
) : ViewModel() {
    private val _settings = MutableLiveData<Setting>()
    val settings get() = _settings

    init {
        getSettings()
    }

    private fun getSettings() = viewModelScope.launch {
        settingUseCases.getSettings().collectLatest {
            _settings.postValue(it)
        }
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settingUseCases.saveThemeSetting(isDarkModeActive)
        }
    }

    fun saveAlertSetting(isAlertActive: Boolean) {
        viewModelScope.launch {
            settingUseCases.saveAlertSetting(isAlertActive)
        }
    }

}