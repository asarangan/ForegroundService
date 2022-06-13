package com.example.foregroundservice

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ServiceNotification() {

    fun showNotification(context: Context):Notification {
        val CHANNEL_ID = "MY_CHANNEL_ID"
        val CHANNEL_NAME = "MY_CHANNEL_NAME"

        val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(context)
        val intent: Intent = Intent(context, MainActivity::class.java)
        val pendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notification: Notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("MyService")
            .setContentText("Running")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notification_background)
            .build()

        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        channel.lightColor = Color.GREEN
        channel.enableLights(true)
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
            createNotificationChannel(channel)
        }

        Log.d(TAG, "Show Notification")
        //notificationManager.notify(0, notification)

        return notification
    }
}