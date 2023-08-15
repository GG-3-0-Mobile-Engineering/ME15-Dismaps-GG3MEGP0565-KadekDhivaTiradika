package com.bydhiva.dismaps.fake

import com.bydhiva.dismaps.data.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSettingRepository : SettingRepository {
    private val settingData = mutableMapOf<String, Boolean>()

    override fun getBoolean(key: String): Flow<Boolean> = flow {
        emit(settingData[key] ?: false)
    }

    override suspend fun saveBoolean(key: String, value: Boolean) {
        settingData[key] = value
    }

    fun getSettingData(): Map<String, Boolean> = settingData
}