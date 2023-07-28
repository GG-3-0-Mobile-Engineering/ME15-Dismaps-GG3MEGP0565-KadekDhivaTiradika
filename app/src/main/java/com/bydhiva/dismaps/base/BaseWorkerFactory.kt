package com.bydhiva.dismaps.base

import android.app.NotificationManager
import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.bydhiva.dismaps.domain.usecase.DisasterUseCases
import com.bydhiva.dismaps.worker.WaterLevelWorker

class BaseWorkerFactory (
    private val notificationManager: NotificationManager,
    private val disasterUseCases: DisasterUseCases
): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        when (workerClassName) {
            WaterLevelWorker::class.java.name ->
                return WaterLevelWorker(appContext, workerParameters, notificationManager, disasterUseCases)
            else -> throw RuntimeException("unsupported worker class: $workerClassName")
        }
    }
}