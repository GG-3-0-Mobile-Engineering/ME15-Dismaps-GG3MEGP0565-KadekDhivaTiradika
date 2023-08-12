package com.bydhiva.dismaps.domain.usecase.setting

import com.bydhiva.dismaps.data.repository.SettingRepository
import com.bydhiva.dismaps.domain.model.Setting
import com.bydhiva.dismaps.domain.model.SettingType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface GetSettingsUseCase {
    operator fun invoke(): Flow<Setting>
}

class GetSettingsUseCaseImpl @Inject constructor(
    private val settingRepository: SettingRepository
) : GetSettingsUseCase {
    override fun invoke(): Flow<Setting> = combine(
    settingRepository.getBoolean(SettingType.DARK_MODE.toString()),
    settingRepository.getBoolean(SettingType.WATER_LEVEL_ALERT.toString())
    ) { f1, f2 -> Setting(f1, f2) }
}