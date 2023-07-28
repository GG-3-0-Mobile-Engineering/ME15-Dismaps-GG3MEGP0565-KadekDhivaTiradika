package com.bydhiva.dismaps.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences(private val dataStore: DataStore<Preferences>){

    private val themeKey = booleanPreferencesKey("theme_setting")
    private val alertKey = booleanPreferencesKey("alert_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[themeKey] ?: false
        }
    }

    fun getAlertSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[alertKey] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }

    suspend fun saveAlertSetting(isAlertActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[alertKey] = isAlertActive
        }
    }

}