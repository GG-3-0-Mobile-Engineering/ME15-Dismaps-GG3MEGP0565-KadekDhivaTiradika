package com.bydhiva.dismaps.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SettingRepository {
    fun getThemeSetting(): Flow<Boolean>
    fun getAlertSetting(): Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
    suspend fun saveAlertSetting(isAlertActive: Boolean)
}

class SettingRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : SettingRepository {
    private val themeKey = booleanPreferencesKey("theme_setting")
    private val alertKey = booleanPreferencesKey("alert_setting")
    override fun getThemeSetting(): Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[themeKey] ?: false
        }

    override fun getAlertSetting(): Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[alertKey] ?: false
        }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }

    override suspend fun saveAlertSetting(isAlertActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[alertKey] = isAlertActive
        }
    }

}