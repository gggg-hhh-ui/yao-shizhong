package com.yaozhen.clock

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class ClockApplication : Application() {

    companion object {
        const val CHANNEL_ALARM = "alarm_channel"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        val channel = NotificationChannel(
            CHANNEL_ALARM,
            "闹钟提醒",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "闹钟响铃通知"
            enableVibration(true)
            setBypassDnd(true)
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}
