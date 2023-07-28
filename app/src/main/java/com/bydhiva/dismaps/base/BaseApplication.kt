package com.bydhiva.dismaps.base

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.Configuration
import com.bydhiva.dismaps.common.preferencesName
import com.bydhiva.dismaps.data.DisasterRepositoryImpl
import com.bydhiva.dismaps.data.datastore.SettingPreferences
import com.bydhiva.dismaps.domain.usecase.DisasterUseCases
import com.bydhiva.dismaps.domain.usecase.GetReports
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class BaseApplication : Application(), Configuration.Provider {
    private val dataStore by preferencesDataStore(preferencesName)
    private val disasterRepository by lazy {
        DisasterRepositoryImpl(NetworkModule.provideApiService())
    }
    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    val appContainer = AppContainer()

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

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(
                BaseWorkerFactory(
                    notificationManager,
                    DisasterUseCases(GetReports(disasterRepository))
                )
            )
            .build()
    }

    fun createMainContainer() {
        appContainer.mainContainer = MainContainer(disasterRepository)
    }

    fun destroyMainContainer() {
        appContainer.mainContainer = null
    }

    fun createSettingContainer() {
        appContainer.settingContainer = SettingContainer(dataStore)
    }

    fun destroySettingContainer() {
        appContainer.settingContainer = null
    }
}