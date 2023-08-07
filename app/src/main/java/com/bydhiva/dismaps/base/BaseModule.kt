package com.bydhiva.dismaps.base

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import com.bydhiva.dismaps.data.DisasterRepository
import com.bydhiva.dismaps.data.DisasterRepositoryImpl
import com.bydhiva.dismaps.data.network.ApiService
import com.bydhiva.dismaps.domain.usecase.disaster.DisasterUseCases
import com.bydhiva.dismaps.domain.usecase.disaster.GetReports
import com.bydhiva.dismaps.domain.usecase.worker.GetReports as GetReportsWorker
import com.bydhiva.dismaps.domain.usecase.worker.WorkerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

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

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDisasterRepository(apiService: ApiService): DisasterRepository =
        DisasterRepositoryImpl(apiService)
}

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideDisasterUseCases(disasterRepository: DisasterRepository): DisasterUseCases =
        DisasterUseCases(
            getReports = GetReports(disasterRepository)
        )
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

@Module
@InstallIn(SingletonComponent::class)
class WorkerUseCaseModule {

    @Provides
    @Singleton
    fun provideWorkerUseCases(disasterRepository: DisasterRepository): WorkerUseCases =
        WorkerUseCases(
            getReports = GetReportsWorker(disasterRepository)
        )
}