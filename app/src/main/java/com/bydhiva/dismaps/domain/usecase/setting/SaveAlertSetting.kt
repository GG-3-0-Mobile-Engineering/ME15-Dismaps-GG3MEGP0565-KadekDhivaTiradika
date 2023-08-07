package com.bydhiva.dismaps.domain.usecase.setting

import com.bydhiva.dismaps.data.repository.SettingRepository

class SaveAlertSetting(private val settingRepository: SettingRepository) {
    suspend operator fun invoke(isAlertActive: Boolean) = settingRepository.saveAlertSetting(isAlertActive)
}