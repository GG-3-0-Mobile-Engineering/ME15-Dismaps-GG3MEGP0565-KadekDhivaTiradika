package com.bydhiva.dismaps.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.bydhiva.dismaps.common.preferencesName
import com.bydhiva.dismaps.data.datastore.SettingPreferences
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application(), Configuration.Provider {
    private val dataStore by preferencesDataStore(preferencesName)
    val appContainer = AppContainer()
    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        LibraryModule.initializeDI(this)
        runBlocking {
            SettingPreferences(dataStore).getThemeSetting().first().let {
                AppCompatDelegate.setDefaultNightMode(
                    if (it) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()

    fun createSettingContainer() {
        appContainer.settingContainer = SettingContainer(dataStore)
    }

    fun destroySettingContainer() {
        appContainer.settingContainer = null
    }
}