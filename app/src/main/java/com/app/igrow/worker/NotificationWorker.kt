package com.app.igrow.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.app.igrow.R
import java.util.concurrent.TimeUnit

class NotificationWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        createNotificationChannel()
        showNotification("IGrow Notification", "Reminder,Dont forget to check new products")
        val notificationWorkRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(applicationContext)
            .enqueue(notificationWorkRequest)
        return Result.success()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "First Notification"
            val description = "notification description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Channel Id", name, importance)
            channel.description = description
            val manager: NotificationManager =
                ContextCompat.getSystemService(
                    applicationContext,
                    NotificationManager::class.java
                ) as NotificationManager

            manager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(Title: String?, Message: String?) {
        val builder = NotificationCompat.Builder(applicationContext, "Channel Id")
        builder.setSmallIcon(R.drawable.igrow_logo)
            .setContentTitle(Title)
            .setContentText(Message)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
        val manager = NotificationManagerCompat.from(applicationContext)
        manager.notify(1, builder.build())
    }

}