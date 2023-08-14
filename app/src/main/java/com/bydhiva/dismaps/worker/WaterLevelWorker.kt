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
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.base.Status
import com.bydhiva.dismaps.common.CHANNEL_ID
import com.bydhiva.dismaps.common.CHANNEL_NAME
import com.bydhiva.dismaps.common.NOTIFICATION_ID
import com.bydhiva.dismaps.domain.usecase.disaster.GetWaterLevelUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.last

@HiltWorker
class WaterLevelWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val notificationManager: NotificationManager,
    private val getWaterLevelUseCase: GetWaterLevelUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        getWaterLevelUseCase().last().let { result ->
            return if (result is Status.Success) {
                result.data?.let {
                    showNotification(context.getString(R.string.water_level_depth, it))
                    Result.success()
                }
                Result.success()
            } else {
                Result.failure()
            }
        }
    }

    private fun showNotification(text: String) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_round_flood_24)
            .setContentTitle(context.getString(R.string.water_level_alert))
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = text
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