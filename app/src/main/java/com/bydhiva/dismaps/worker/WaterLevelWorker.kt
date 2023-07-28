package com.bydhiva.dismaps.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.base.Status
import com.bydhiva.dismaps.common.CHANNEL_ID
import com.bydhiva.dismaps.common.CHANNEL_NAME
import com.bydhiva.dismaps.common.NOTIFICATION_ID
import com.bydhiva.dismaps.domain.model.DisasterType
import com.bydhiva.dismaps.domain.usecase.DisasterUseCases
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last

class WaterLevelWorker(
    private val context: Context,
    private val workerParams: WorkerParameters,
    private val notificationManager: NotificationManager,
    private val disasterUseCases: DisasterUseCases
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        // Should use water level api, due inconsistent api and internal server error
        // use getReports flood depth as value
        val res = disasterUseCases.getReports(disasterType = DisasterType.FLOOD).last()
        if (res is Status.Success) {
            if (res.data?.isNotEmpty() == true) {
                val depth = res.data.first().depth
                showNotification("Water level depth: $depth")
            }
            return Result.success()
        }
        return Result.failure()
    }

    private fun showNotification(text: String) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_round_flood_24)
            .setContentTitle(context.getString(R.string.water_level_alert))
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = text
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }


}