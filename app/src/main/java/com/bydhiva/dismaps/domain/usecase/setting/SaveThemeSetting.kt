package com.bydhiva.dismaps.domain.usecase.setting

import com.bydhiva.dismaps.data.repository.SettingRepository

class SaveThemeSetting(private val settingRepository: SettingRepository) {
    suspend operator fun invoke(isDarkModeActive: Boolean) = settingRepository.saveThemeSetting(isDarkModeActive)
}