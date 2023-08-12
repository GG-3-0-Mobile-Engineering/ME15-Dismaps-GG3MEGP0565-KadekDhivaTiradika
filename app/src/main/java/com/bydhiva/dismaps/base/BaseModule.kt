package com.bydhiva.dismaps.base

import android.app.NotificationManager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.bydhiva.dismaps.data.network.ApiService
import com.bydhiva.dismaps.data.repository.DisasterRepository
import com.bydhiva.dismaps.data.repository.DisasterRepositoryImpl
import com.bydhiva.dismaps.data.repository.SettingRepository
import com.bydhiva.dismaps.data.repository.SettingRepositoryImpl
import com.bydhiva.dismaps.domain.usecase.disaster.GetReportsUseCase
import com.bydhiva.dismaps.domain.usecase.disaster.GetReportsUseCaseImpl
import com.bydhiva.dismaps.domain.usecase.disaster.GetWaterLevelUseCase
import com.bydhiva.dismaps.domain.usecase.disaster.GetWaterLevelUseCaseImpl
import com.bydhiva.dismaps.domain.usecase.setting.GetSettingsUseCase
import com.bydhiva.dismaps.domain.usecase.setting.GetSettingsUseCaseImpl
import com.bydhiva.dismaps.domain.usecase.setting.SaveSettingUseCase
import com.bydhiva.dismaps.domain.usecase.setting.SaveSettingUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val gsonConverterFactory by lazy { GsonConverterFactory.create() }
    private val loggingInterceptor by lazy { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
    private const val url = "https://data.petabencana.id/"

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()
        .create(ApiService::class.java)
}

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {
    private const val preferencesName = "settings"

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile(preferencesName) }
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsDisasterRepository(
        disasterRepositoryImpl: DisasterRepositoryImpl
    ): DisasterRepository

    @Binds
    abstract fun bindsSettingRepository(
        settingRepositoryImpl: SettingRepositoryImpl
    ): SettingRepository
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelScopedUseCaseModule {

    @Binds
    abstract fun bindsGetReportsUseCase(
        getReportsUseCaseImpl: GetReportsUseCaseImpl
    ): GetReportsUseCase
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SingletonScopedUseCaseModule {
    @Binds
    abstract fun bindGetSettingsUseCase(
        getSettingsUseCaseImpl: GetSettingsUseCaseImpl
    ): GetSettingsUseCase

    @Binds
    abstract fun bindSaveSettingUseCase(
        saveSettingUseCaseImpl: SaveSettingUseCaseImpl
    ): SaveSettingUseCase

    @Binds
    abstract fun bindGetWaterLevelModule(
        getWaterLevelUseCaseImpl: GetWaterLevelUseCaseImpl
    ): GetWaterLevelUseCase
}

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}
