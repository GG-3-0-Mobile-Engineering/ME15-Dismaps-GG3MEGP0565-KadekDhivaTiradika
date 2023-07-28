package com.bydhiva.dismaps.base

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.bydhiva.dismaps.data.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import androidx.datastore.preferences.preferencesDataStore
import com.bydhiva.dismaps.common.preferencesName
import com.bydhiva.dismaps.data.datastore.SettingPreferences

object LibraryModule {
    @Volatile
    lateinit var application: BaseApplication

    fun initializeDI(baseApplication: BaseApplication) {
        if (!::application.isInitialized) {
            synchronized(this) {
                this.application = baseApplication
            }
        }
    }

}

object NetworkModule {
    private val gsonConverterFactory by lazy { GsonConverterFactory.create() }
    private val loggingInterceptor by lazy { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    private const val url = "https://data.petabencana.id/"

    fun provideApiService(): ApiService = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()
        .create(ApiService::class.java)
}

class DatastoreModule {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(preferencesName)

    fun Context.getSettingPreferences() = SettingPreferences(this.dataStore)
}