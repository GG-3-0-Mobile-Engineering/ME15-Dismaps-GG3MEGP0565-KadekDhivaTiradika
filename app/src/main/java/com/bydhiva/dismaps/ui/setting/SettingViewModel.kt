package com.bydhiva.dismaps.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bydhiva.dismaps.data.datastore.SettingPreferences
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingViewModel(
    private val pref: SettingPreferences?
) : ViewModel() {

    private val _themeSetting = MutableLiveData<Boolean>()
    val themeSetting get() = _themeSetting

    private val _alertSetting = MutableLiveData<Boolean>()
    val alertSetting get() = _alertSetting

    init {
        getThemeSettings()
        getAlertSetting()
    }

    private fun getThemeSettings() = viewModelScope.launch {
        if (pref == null) return@launch
        pref.getThemeSetting().collectLatest {
            _themeSetting.postValue(it)
        }
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        if (pref == null) return
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    private fun getAlertSetting() = viewModelScope.launch {
        if (pref == null) return@launch
        pref.getAlertSetting().collectLatest {
            _alertSetting.postValue(it)
        }
    }

    fun saveAlertSetting(isAlertActive: Boolean) {
        if (pref == null) return
        viewModelScope.launch {
            pref.saveAlertSetting(isAlertActive)
        }
    }

}