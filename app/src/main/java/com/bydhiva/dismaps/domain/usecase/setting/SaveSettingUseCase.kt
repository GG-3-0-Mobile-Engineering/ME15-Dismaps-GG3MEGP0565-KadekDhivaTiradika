package com.bydhiva.dismaps.domain.usecase.setting

import com.bydhiva.dismaps.data.repository.SettingRepository
import com.bydhiva.dismaps.domain.model.SettingType
import javax.inject.Inject

interface SaveSettingUseCase {
    suspend fun saveBoolean(settingType: SettingType, value: Boolean)
}

class SaveSettingUseCaseImpl @Inject constructor(
    private val settingRepository: SettingRepository
) : SaveSettingUseCase {
    override suspend fun saveBoolean(settingType: SettingType, value: Boolean) =
        settingRepository.saveBoolean(settingType.toString(), value)
}