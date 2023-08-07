package com.bydhiva.dismaps.domain.usecase.setting

import com.bydhiva.dismaps.data.repository.SettingRepository
import com.bydhiva.dismaps.domain.model.Setting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetSettings(private val settingRepository: SettingRepository) {
    operator fun invoke(): Flow<Setting> = combine(
        settingRepository.getThemeSetting(),
        settingRepository.getAlertSetting()
    ) { f1, f2 -> Setting(f1, f2) }
}