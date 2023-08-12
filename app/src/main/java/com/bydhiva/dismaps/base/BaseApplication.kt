package com.bydhiva.dismaps.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.bydhiva.dismaps.domain.usecase.setting.GetSettingsUseCase
import com.bydhiva.dismaps.domain.usecase.setting.GetSettingsUseCaseImpl
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application(), Configuration.Provider {
    @Inject lateinit var getSettingsUseCase: GetSettingsUseCaseImpl
    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        runBlocking {
            getSettingsUseCase().first().isDarkModeActive.let {
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
}